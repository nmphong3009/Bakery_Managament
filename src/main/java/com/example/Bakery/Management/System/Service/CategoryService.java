package com.example.Bakery.Management.System.Service;

import com.example.Bakery.Management.System.DTOS.Request.CategoryRequest;
import com.example.Bakery.Management.System.DTOS.Request.MenuRequest;
import com.example.Bakery.Management.System.DTOS.Response.CategoryResponse;
import com.example.Bakery.Management.System.DTOS.Response.MenuResponse;
import com.example.Bakery.Management.System.DTOS.Response.UserResponse;
import com.example.Bakery.Management.System.Entity.Category;
import com.example.Bakery.Management.System.Entity.MenuItems;
import com.example.Bakery.Management.System.Entity.User;
import com.example.Bakery.Management.System.Repository.CategoryRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Lazy
    private final UserService userService;

    @Lazy
    private final CategoryRepository categoryRepository;

    public CategoryService(UserService userService, CategoryRepository categoryRepository) {
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<?> create (CategoryRequest request){
        if (!userService.isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        if (categoryRepository.findByName(request.getName()).isPresent()){
            throw new RuntimeException("Category already exists!");
        }

        Category category = Category.builder()
                .name(request.getName())
                .build();
        categoryRepository.save(category);
        return ResponseEntity.ok("Create Category successful");
    }

    public ResponseEntity<?> update (CategoryRequest request){
        if (!userService.isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        Category category = categoryRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getId()));
        category.setName(request.getName());
        categoryRepository.save(category);
        return ResponseEntity.ok("Update MenuItem successful");
    }

    public ResponseEntity<?> delete(Long id){
        if (!userService.isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id ));
        categoryRepository.delete(category);
        return ResponseEntity.ok("Delete Category successful");
    }

    public List<CategoryResponse> getAllCategory(){
        if (!userService.isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(
                category -> CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build()
        ).toList();
    }
}
