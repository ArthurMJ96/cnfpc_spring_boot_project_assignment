package lu.arthurmj.cnfpc_spring_boot_project_assignment.factory;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Category;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Product;

@Component
public class ProductFactory {

    /**
     * Creates a Product with a name, description, price (in cents), and categories.
     */
    public Product createProduct(String name, String description, Integer priceInCents, Set<Category> categories,
            List<String> images) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(priceInCents);
        product.setImages(images);

        if (categories != null) {
            categories.forEach(product::addCategory);
        }

        return product;
    }
}