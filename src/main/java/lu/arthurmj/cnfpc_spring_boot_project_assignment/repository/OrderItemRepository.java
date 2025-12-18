package lu.arthurmj.cnfpc_spring_boot_project_assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}