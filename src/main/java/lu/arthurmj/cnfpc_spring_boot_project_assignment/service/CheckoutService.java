package lu.arthurmj.cnfpc_spring_boot_project_assignment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Address;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.CartItem;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Customer;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Order;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.OrderItem;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Product;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.repository.AddressRepository;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.repository.OrderRepository;

@Service
public class CheckoutService {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public Order checkout(Customer customer, Address shippingAddress, boolean persistAddress) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer is required");
        }

        List<CartItem> cartItems = cartService.getItems();
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        if (shippingAddress == null) {
            throw new IllegalArgumentException("Shipping address is required");
        }

        if (persistAddress) {
            shippingAddress.setCustomer(customer);
            shippingAddress = addressRepository.save(shippingAddress);
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setShippingAddress(shippingAddress);
        // Billing address is the same as shipping for now
        order.setBillingAddress(shippingAddress);

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Product product = productService.findById(cartItem.getProductId());
            if (product == null) {
                throw new EntityNotFoundException(
                        "Product with ID '" + cartItem.getProductId() + "' not found.");
            }

            inventoryService.decrementStockOrThrow(product.getId(), cartItem.getQuantity());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        Order saved = orderRepository.save(order);
        cartService.clear();
        return saved;
    }
}
