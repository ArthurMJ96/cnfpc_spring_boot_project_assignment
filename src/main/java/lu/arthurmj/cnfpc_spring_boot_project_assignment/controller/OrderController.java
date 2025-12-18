package lu.arthurmj.cnfpc_spring_boot_project_assignment.controller;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.persistence.EntityNotFoundException;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.enums.OrderStatus;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Customer;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Order;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.CustomerService;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.OrderService;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    // #region Public Order Views
    @GetMapping("/orders")
    public String listOrders(Authentication authentication, Model model) {
        Customer customer = customerService.findByEmail(authentication.getName());
        model.addAttribute("orders", orderService.findForCustomerId(customer.getId()));
        return "pages/orders/list";
    }

    @GetMapping("/orders/{orderId}")
    public String orderDetail(
            @PathVariable String orderId,
            Authentication authentication,
            Model model) {

        Customer customer = customerService.findByEmail(authentication.getName());
        Order order = orderService.findByIdForCustomer(orderId, customer.getId());
        if (order == null) {
            throw new EntityNotFoundException("Order with ID '" + orderId + "' not found.");
        }

        model.addAttribute("order", order);
        return "pages/orders/detail";
    }
    // #endregion

    // #region Admin Orders Views (Employees only)
    @GetMapping("/admin/orders/list")
    public String listAllOrders(Model model) {
        model.addAttribute("orders", orderService.findAllNewestFirst());
        return "pages/orders/admin/list";
    }

    @GetMapping("/admin/orders/{orderId}")
    public String orderDetail(@PathVariable String orderId, Model model) {
        Order order = orderService.findByIdForAdmin(orderId);
        if (order == null) {
            throw new EntityNotFoundException("Order with ID '" + orderId + "' not found.");
        }

        model.addAttribute("order", order);
        model.addAttribute("statuses", OrderStatus.values());
        return "pages/orders/admin/detail";
    }

    @PostMapping("/admin/orders/{orderId}/status")
    public String updateStatus(
            @PathVariable String orderId,
            @RequestParam OrderStatus status,
            RedirectAttributes redirectAttributes) {

        Order updated = orderService.updateStatus(orderId, status);
        if (updated == null) {
            throw new EntityNotFoundException("Order with ID '" + orderId + "' not found.");
        }

        redirectAttributes.addFlashAttribute("orderSuccess", "Order status updated.");
        return "redirect:/admin/orders/" + orderId;
    }
    // #endregion
}
