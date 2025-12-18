package lu.arthurmj.cnfpc_spring_boot_project_assignment.factory;

import org.springframework.stereotype.Component;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Category;

@Component
public class CategoryFactory {

    /**
     * Creates a Category with a name.
     */
    public Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return category;
    }
}