package lu.arthurmj.cnfpc_spring_boot_project_assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    Category findTop1ByOrderByIdAsc();

    Category findById(long id);

    void deleteById(Long id);
}