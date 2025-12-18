package lu.arthurmj.cnfpc_spring_boot_project_assignment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProduct_Id(Long productId);
}
