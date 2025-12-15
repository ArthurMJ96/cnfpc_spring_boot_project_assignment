package lu.arthurmj.cnfpc_spring_boot_project_assignment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalTemplateAttributes {
    @Value("${app.name}")
    private String appTitle;

    @ModelAttribute("appTitle")
    public String addGlobalAttributes() {
        return appTitle;
    }

    // @ModelAttribute("Roles")
    // public Class<Role> getRolesClass() {
    // return Role.class;
    // }
}
