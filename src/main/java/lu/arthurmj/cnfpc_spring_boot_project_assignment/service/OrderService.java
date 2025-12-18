package lu.arthurmj.cnfpc_spring_boot_project_assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.enums.OrderStatus;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Order;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findForCustomerId(String customerId) {
        return customerId == null ? List.of() : orderRepository.findByCustomer_IdOrderByOrderDateDesc(customerId);
    }

    public List<Order> findAllNewestFirst() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    public Order findByIdForAdmin(String orderId) {
        return orderId == null ? null : orderRepository.findById(orderId).orElse(null);
    }

    @Transactional
    public Order updateStatus(String orderId, OrderStatus status) {
        if (orderId == null || status == null) {
            return null;
        }

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }

        order.setOrderStatus(status);
        return orderRepository.save(order);
    }

    public Order findByIdForCustomer(String orderId, String customerId) {
        if (orderId == null || customerId == null) {
            return null;
        }
        return orderRepository.findByIdAndCustomer_Id(orderId, customerId).orElse(null);
    }
}
