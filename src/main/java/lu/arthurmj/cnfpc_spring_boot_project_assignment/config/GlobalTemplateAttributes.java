package lu.arthurmj.cnfpc_spring_boot_project_assignment.config;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalTemplateAttributes {
    @Value("${app.name}")
    private String appTitle;

    /** Exposes the application title to Thymeleaf templates. */
    @ModelAttribute("appTitle")
    public String addGlobalAttributes() {
        return appTitle;
    }

    /**
     * Exposes a Function to Thymeleaf to check for active paths.
     * Returns "active" if the current path contains with the given path, else "".
     * 
     * @example `<div th:class="${uc.apply('/products')}">...</div>`
     */
    @ModelAttribute("uc")
    public Function<String, String> urlContainsActive(HttpServletRequest request) {
        String current = request.getRequestURI();
        return path -> current != null && current.contains(path) ? "active" : "";
    }
}
