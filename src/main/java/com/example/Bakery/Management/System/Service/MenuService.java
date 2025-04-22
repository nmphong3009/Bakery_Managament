package com.example.Bakery.Management.System.Service;

import com.example.Bakery.Management.System.DTOS.Request.MenuRequest;
import com.example.Bakery.Management.System.DTOS.Response.MenuResponse;
import com.example.Bakery.Management.System.Entity.Category;
import com.example.Bakery.Management.System.Entity.MenuItems;
import com.example.Bakery.Management.System.Repository.CategoryRepository;
import com.example.Bakery.Management.System.Repository.MenuRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Lazy
    private final MenuRepository menuRepository;

    @Lazy
    private final UserService userService;

    @Lazy
    private final CategoryRepository categoryRepository;

    public MenuService(MenuRepository menuRepository, UserService userService, CategoryRepository categoryRepository) {
        this.menuRepository = menuRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<?> create (MenuRequest request){
        if (!userService.isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        if (menuRepository.findByName(request.getName()).isPresent()){
            throw new RuntimeException("MenuItem already exists!");
        }

        if (categoryRepository.findById(request.getCategoryId()).isPresent()){
            throw new RuntimeException("Category already exists!");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));

        MenuItems menuItems = MenuItems.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .priceCost(request.getPriceCost())
                .category(category)
                .build();
        menuRepository.save(menuItems);
        return ResponseEntity.ok("Create MenuItem successful");
    }

    public ResponseEntity<?> update (MenuRequest request){
        if (!userService.isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        if (categoryRepository.findById(request.getCategoryId()).isPresent()){
            throw new RuntimeException("Category already exists!");
        }
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));
        MenuItems menuItems = menuRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + request.getId()));
        menuItems.setName(request.getName());
        menuItems.setDescription(request.getDescription());
        menuItems.setPrice(request.getPrice());
        menuItems.setPriceCost(request.getPriceCost());
        menuItems.setCategory(category);
        menuRepository.save(menuItems);
        return ResponseEntity.ok("Update MenuItem successful");
    }

    public ResponseEntity<?> delete(Long id){
        if (!userService.isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        MenuItems menuItems = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + id ));
        menuRepository.delete(menuItems);
        return ResponseEntity.ok("Delete MenuItem successful");
    }

    public List<MenuResponse> getMenu(Long categoryId){
        List<MenuItems> menuItemsList = menuRepository.findAllByCategoryId(categoryId);
        return menuItemsList.stream().map(
                menuItems -> MenuResponse.builder()
                        .id(menuItems.getId())
                        .name(menuItems.getName())
                        .description(menuItems.getDescription())
                        .price(menuItems.getPrice())
                        .priceCost(menuItems.getPriceCost())
                        .build()
        ).toList();
    }

}

