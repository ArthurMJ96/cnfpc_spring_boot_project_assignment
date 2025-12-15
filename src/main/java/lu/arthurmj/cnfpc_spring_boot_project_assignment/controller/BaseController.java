package lu.arthurmj.cnfpc_spring_boot_project_assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping("/")
    public String getBase(Model model) {
        return "pages/home";
    }

}
