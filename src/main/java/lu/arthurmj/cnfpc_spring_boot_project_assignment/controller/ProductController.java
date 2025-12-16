package lu.arthurmj.cnfpc_spring_boot_project_assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Product;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.CategoryService;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.ProductService;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // #region Public Product Views

    @GetMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "pages/product/grid";
    }

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        if (product == null) {
            return "errors/404";
        }
        model.addAttribute("product", product);
        return "pages/product/detail";
    }
    // #endregion

    // #region (Employees only) Product Management
    @GetMapping("/admin/products/list")
    public String getProductsList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "pages/product/list";
    }

    @GetMapping("/admin/products/new")
    public String getNewProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "pages/product/form";
    }

    @PostMapping("/admin/products/save")
    public String saveProduct(@Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.findAll());
            return "pages/product/form";
        }
        productService.save(product);
        return "redirect:/product/" + product.getId();
    }

    @GetMapping("/admin/products/edit/{id}")
    public String getEditProductForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        if (product == null) {
            return "errors/404";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        return "pages/product/form";
    }

    @PostMapping("/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/admin/products/list";
    }

    // #endregion
}
