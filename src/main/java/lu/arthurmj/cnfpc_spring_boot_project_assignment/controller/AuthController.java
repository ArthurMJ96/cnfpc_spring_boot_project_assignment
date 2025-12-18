package lu.arthurmj.cnfpc_spring_boot_project_assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.exception.CustomerEmailExistsException;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Customer;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.CustomerService;

@Controller
public class AuthController {
    @Autowired
    private CustomerService customerService;

    // Login form getter
    @GetMapping("/login")
    public String getLogin(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("customer", new Customer());
        return "pages/auth/login";
    }

    // Register Form getter
    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("customer", new Customer());
        return "pages/auth/register";
    }

    // Register Form post handler
    @PostMapping("/register")
    public String postRegister(@Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "pages/auth/register";
        }

        // Check if passwords match
        if (!customerService.validatePasswords(customer)) {
            result.rejectValue("confirmPassword", "password.mismatch", "Passwords do not match");
            model.addAttribute("customer", customer);
            return "pages/auth/register";
        }
        try {
            // Try to create the customer
            customerService.create(customer);
        } catch (CustomerEmailExistsException e) {
            // Email already exists error
            result.rejectValue("email", "email.exists", e.getMessage());
            model.addAttribute("customer", customer);
            return "pages/auth/register";
        }

        return "redirect:/login";
    }
}
