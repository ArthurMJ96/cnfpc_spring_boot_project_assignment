package lu.arthurmj.cnfpc_spring_boot_project_assignment.factory;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Category;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Inventory;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Product;

@Component
public class ProductFactory {

    /**
     * Creates a Product with a name, description, price (in cents), categories and
     * Inventory.
     */
    public Product createProduct(String name, String description, Integer priceInCents, Set<Category> categories,
            List<String> images, Integer inventoryAmount) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(priceInCents);
        product.setImages(images);

        Inventory inventory = new Inventory();
        inventory.setAmount(inventoryAmount == null ? 0 : Math.max(0, inventoryAmount));
        inventory.setProduct(product);
        product.setInventory(inventory);

        if (categories != null) {
            categories.forEach(product::addCategory);
        }

        return product;
    }
}