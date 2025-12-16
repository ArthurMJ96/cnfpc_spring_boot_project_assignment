package lu.arthurmj.cnfpc_spring_boot_project_assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.exception.CategoryExistsException;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Category;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.CategoryService;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // #region Public Category Views
    @GetMapping("/category")
    public String getCategories(Model model) {
        Category firstCategory = categoryService.findTop1();
        if (firstCategory != null) {
            return "redirect:/category/" + firstCategory.getName();
        }
        return "pages/category/grid";
    }

    @GetMapping("/category/{categoryName}")
    public String getCategoryProducts(@PathVariable String categoryName, Model model) {
        List<Category> categories = categoryService.findAll();
        if (categoryName != null) {
            Category category = categoryService.findByName(categoryName);
            model.addAttribute("category", category);
            if (category != null) {
                model.addAttribute("products", category.getProducts());
            }
        }
        model.addAttribute("categories", categories);

        return "pages/category/grid";
    }

    // #endregion

    // #region (Employees only) Category Management
    @GetMapping("admin/category/list")
    public String getCategoriesList(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "pages/category/list";
    }

    @GetMapping("admin/category/new")
    public String getNewCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "pages/category/form";
    }

    @PostMapping("admin/category/save")
    public String saveCategory(@Valid Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("category", category);
            return "pages/category/form";
        }

        // Category exists, perform update
        if (category.getId() != null) {
            categoryService.save(category);
            return "redirect:/category/" + category.getName();
        }

        // New category, try creation
        try {
            categoryService.create(category);
        } catch (CategoryExistsException e) {
            model.addAttribute("category", category);
            model.addAttribute("error", e.getMessage());
            return "pages/category/form";
        }
        return "redirect:/category/" + category.getName();

    }

    @GetMapping("admin/category/edit/{id}")
    public String getEditCategoryForm(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        if (category == null) {
            throw new EntityNotFoundException("Category with ID '" + id + "' not found.");
        }
        model.addAttribute("category", category);
        return "pages/category/form";
    }

    @PostMapping("admin/category/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/admin/category/list";
    }
    // #endregion
}
