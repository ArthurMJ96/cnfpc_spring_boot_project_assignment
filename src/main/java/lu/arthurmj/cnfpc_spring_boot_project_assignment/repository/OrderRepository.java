package lu.arthurmj.cnfpc_spring_boot_project_assignment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

    @EntityGraph(attributePaths = { "orderItems", "orderItems.product" })
    List<Order> findByCustomer_IdOrderByOrderDateDesc(String customerId);

    @EntityGraph(attributePaths = { "orderItems", "orderItems.product", "shippingAddress", "billingAddress" })
    Optional<Order> findByIdAndCustomer_Id(String id, String customerId);

    @Override
    @EntityGraph(attributePaths = { "orderItems", "orderItems.product", "customer", "shippingAddress",
            "billingAddress" })
    Optional<Order> findById(String id);

    @EntityGraph(attributePaths = { "orderItems", "orderItems.product", "customer" })
    List<Order> findAllByOrderByOrderDateDesc();
}