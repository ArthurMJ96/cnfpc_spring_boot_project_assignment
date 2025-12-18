package lu.arthurmj.cnfpc_spring_boot_project_assignment.config;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.CartService;

/**
 * Provides global attributes to Thymeleaf templates.
 */
@ControllerAdvice
public class GlobalTemplateAttributes {
    @Value("${app.name}")
    private String appTitle;

    @Autowired
    private CartService cartService;

    /** Exposes the application title to Thymeleaf templates. */
    @ModelAttribute("appTitle")
    public String addGlobalAttributes() {
        return appTitle;
    }

    /**
     * Exposes a Function to Thymeleaf to check for active paths.
     * Returns "active" if the current path contains with the given path, else "".
     * 
     * @example `<div th:class="${uca.apply('/products')}">...</div>`
     */
    @ModelAttribute("uca")
    public Function<String, String> urlContainsActive(HttpServletRequest request) {
        String current = request.getRequestURI();
        return path -> current != null && current.contains(path) ? "active" : "";
    }

    /**
     * Exposes a Function to Thymeleaf to check for active paths.
     * Returns "active" if the current path starts with the given path, else "".
     * 
     * @example `<div th:class="${usa.apply('/products')}">...</div>`
     */
    @ModelAttribute("usa")
    public Function<String, String> urlStartsActive(HttpServletRequest request) {
        String current = request.getRequestURI();
        return path -> current != null && current.startsWith(path) ? "active" : "";
    }

    /** Exposes the cart item count to Thymeleaf templates. */
    @ModelAttribute("cartItemCount")
    public int cartItemCount() {
        return cartService.getTotalQuantity();
    }
}
