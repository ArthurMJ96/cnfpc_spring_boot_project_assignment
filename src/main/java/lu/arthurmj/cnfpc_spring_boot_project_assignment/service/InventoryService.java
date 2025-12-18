package lu.arthurmj.cnfpc_spring_boot_project_assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.exception.OutOfStockException;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Inventory;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Product;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.repository.InventoryRepository;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * Ensures a Product has a non-null Inventory instance wired both ways.
     * Does not persist by itself (caller typically persists the Product).
     */
    public Inventory ensureInventory(Product product) {
        if (product == null) {
            return null;
        }

        Inventory inventory = product.getInventory();
        if (inventory == null) {
            inventory = new Inventory();
            // Default stock for newly created products / legacy records.
            inventory.setAmount(10);
            product.setInventory(inventory);
        }

        if (inventory.getProduct() == null || inventory.getProduct() != product) {
            inventory.setProduct(product);
        }

        return inventory;
    }

    /**
     * Gets available stock for a given product.
     */
    public int getAvailable(Long productId) {
        if (productId == null) {
            return 0;
        }
        return inventoryRepository.findByProduct_Id(productId)
                .map(inv -> inv.getAmount() == null ? 0 : inv.getAmount())
                .orElse(0);
    }

    public boolean hasSufficientStock(Long productId, int requestedQuantity) {
        int requested = Math.max(0, requestedQuantity);
        return requested <= getAvailable(productId);
    }

    /**
     * Decrements stock. Intended for a real checkout flow.
     */
    @Transactional
    public void decrementStockOrThrow(Long productId, int quantity) {
        int requested = Math.max(0, quantity);
        int available = getAvailable(productId);
        if (requested > available) {
            throw new OutOfStockException(productId, requested, available);
        }

        Inventory inv = inventoryRepository.findByProduct_Id(productId)
                .orElseThrow(() -> new OutOfStockException(productId, requested, 0));

        int current = inv.getAmount() == null ? 0 : inv.getAmount();
        inv.setAmount(current - requested);
        inventoryRepository.save(inv);
    }
}
