package lu.arthurmj.cnfpc_spring_boot_project_assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Customer;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.CustomerService;

@Controller
public class ProfileController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/profile")
    public String showProfile(Authentication authentication, Model model) {
        Customer customer = customerService.findByEmail(authentication.getName());
        model.addAttribute("customer", customer);
        return "pages/profile/profile";
    }

    @PostMapping("/profile/update/password")
    public String updateProfile(@Valid Customer customer,
            BindingResult result,
            RedirectAttributes redirectAttribute,
            Authentication authentication,
            Model model) {
        if (result.hasErrors()) {
            return "profile";
        }
        Customer existing = (Customer) authentication.getPrincipal();

        // Check if id matches
        if (!existing.getId().equals(customer.getId())) {
            redirectAttribute.addFlashAttribute("error", "ID mismatch error");
            return "redirect:/profile";
        }

        // Check if passwords match
        if (!customerService.validatePasswords(customer)) {
            redirectAttribute.addFlashAttribute("error", "Passwords do not match");
            return "redirect:/profile";
        }

        customerService.updatePassword(existing, customer.getPassword());
        redirectAttribute.addFlashAttribute("success", "Password Updated!");
        return "redirect:/profile";
    }
}
