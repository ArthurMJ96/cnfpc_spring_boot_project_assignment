package lu.arthurmj.cnfpc_spring_boot_project_assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findTop8By();

    // Find products by category
    List<Product> findByCategories_Name(String categoryName);
}
