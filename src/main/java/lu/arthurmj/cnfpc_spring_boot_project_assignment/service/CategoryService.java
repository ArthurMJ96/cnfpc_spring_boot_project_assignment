package lu.arthurmj.cnfpc_spring_boot_project_assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.exception.CategoryExistsException;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Category;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void create(Category category) throws CategoryExistsException {
        if (categoryRepository.findByName(category.getName()) != null) {
            throw new CategoryExistsException("Category with this name already exists");
        }
        categoryRepository.save(category);
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Category findTop1() {
        return categoryRepository.findTop1ByOrderByIdAsc();
    }

    public Category findById(long id) {
        return categoryRepository.findById(id);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
