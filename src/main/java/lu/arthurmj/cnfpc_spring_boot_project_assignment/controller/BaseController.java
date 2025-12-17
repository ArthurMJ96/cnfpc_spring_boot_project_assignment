package lu.arthurmj.cnfpc_spring_boot_project_assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.ProductService;

@Controller
@RequestMapping("/")
public class BaseController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getBase(Model model) {
        model.addAttribute("products", productService.findTop8Products());
        return "pages/home";
    }

}
