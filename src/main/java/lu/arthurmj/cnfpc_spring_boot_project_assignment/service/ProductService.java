package lu.arthurmj.cnfpc_spring_boot_project_assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Inventory;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Product;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.repository.InventoryRepository;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public Product save(Product product) {
        if (product == null) {
            return null;
        }

        Integer submittedAmount = null;
        if (product.getInventory() != null) {
            submittedAmount = product.getInventory().getAmount();
        }

        if (product.getId() != null) {
            // Avoid persisting/merging a new Inventory instance unnecessarily
            Inventory existing = inventoryRepository.findByProduct_Id(product.getId()).orElse(null);
            if (existing != null) {
                if (submittedAmount != null) {
                    existing.setAmount(Math.max(0, submittedAmount));
                }
                product.setInventory(existing);
            } else {
                Inventory inv = new Inventory();
                inv.setAmount(submittedAmount == null ? 10 : Math.max(0, submittedAmount));
                inv.setProduct(product);
                product.setInventory(inv);
            }
        } else {
            Inventory inv = inventoryService.ensureInventory(product);
            if (submittedAmount != null && inv != null) {
                inv.setAmount(Math.max(0, submittedAmount));
            }
        }

        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> findTop8Products() {
        return productRepository.findTop8By();
    }

    public List<Product> findByCategoryName(String categoryName) {
        return productRepository.findByCategories_Name(categoryName);
    }

}
