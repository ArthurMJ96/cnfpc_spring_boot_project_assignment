package lu.arthurmj.cnfpc_spring_boot_project_assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Address;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Customer;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Order;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.AddressService;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.CartService;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.CheckoutService;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.CustomerService;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CheckoutService checkoutService;

    @GetMapping
    public String getCheckout(
            Authentication authentication,
            RedirectAttributes redirectAttributes,
            Model model) {
        if (cartService.isEmpty()) {
            redirectAttributes.addFlashAttribute("cartError", "Your cart is empty.");
            return "redirect:/cart";
        }

        Customer customer = customerService.findByEmail(authentication.getName());
        List<Address> savedAddresses = addressService.findForCustomerId(customer.getId());

        model.addAttribute("items", cartService.getItems());
        model.addAttribute("totalAmount", cartService.getTotalAmount());
        model.addAttribute("savedAddresses", savedAddresses);
        model.addAttribute("shippingAddress", new Address());

        return "pages/checkout/checkout";
    }

    @PostMapping(params = "mode=saved")
    public String postCheckoutSaved(
            @RequestParam(required = false) Long addressId,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        if (cartService.isEmpty()) {
            redirectAttributes.addFlashAttribute("cartError", "Your cart is empty.");
            return "redirect:/cart";
        }

        if (addressId == null) {
            redirectAttributes.addFlashAttribute("checkoutError", "Please select an address.");
            return "redirect:/checkout";
        }

        Customer customer = customerService.findByEmail(authentication.getName());
        Address address = addressService.findById(addressId);

        if (address == null || address.getCustomer() == null
                || !customer.getId().equals(address.getCustomer().getId())) {
            redirectAttributes.addFlashAttribute("checkoutError", "Invalid address selection.");
            return "redirect:/checkout";
        }

        Order order = checkoutService.checkout(customer, address, false);
        return "redirect:/checkout/success?orderId=" + order.getId();
    }

    @PostMapping(params = "mode=new")
    public String postCheckoutNew(
            @Valid @ModelAttribute("shippingAddress") Address shippingAddress,
            BindingResult result,
            Authentication authentication,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (cartService.isEmpty()) {
            redirectAttributes.addFlashAttribute("cartError", "Your cart is empty.");
            return "redirect:/cart";
        }

        Customer customer = customerService.findByEmail(authentication.getName());

        if (result.hasErrors()) {
            model.addAttribute("items", cartService.getItems());
            model.addAttribute("totalAmount", cartService.getTotalAmount());
            model.addAttribute("savedAddresses", addressService.findForCustomerId(customer.getId()));
            return "pages/checkout/checkout";
        }

        shippingAddress.setCustomer(customer);
        Order order = checkoutService.checkout(customer, shippingAddress, true);
        return "redirect:/checkout/success?orderId=" + order.getId();
    }

    @GetMapping("/success")
    public String getSuccess(@RequestParam String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "pages/checkout/success";
    }
}
