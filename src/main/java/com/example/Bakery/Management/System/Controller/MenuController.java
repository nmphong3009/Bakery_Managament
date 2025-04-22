package com.example.Bakery.Management.System.Controller;

import com.example.Bakery.Management.System.DTOS.Request.MenuRequest;
import com.example.Bakery.Management.System.DTOS.Response.MenuResponse;
import com.example.Bakery.Management.System.Entity.Category;
import com.example.Bakery.Management.System.Service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("menu")
public class MenuController {
    @Lazy
    private final MenuService menuService;

    @PostMapping("admin/create")
    public ResponseEntity<?> create (@RequestBody MenuRequest request){
        return ResponseEntity.ok(menuService.create(request));
    }

    @PutMapping("admin/update")
    public ResponseEntity<?> update (@RequestBody MenuRequest request){
        return ResponseEntity.ok(menuService.update(request));
    }

    @DeleteMapping("admin/delete/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        return ResponseEntity.ok(menuService.delete(id));
    }

    @GetMapping("getMenuByCategory")
    public ResponseEntity<List<MenuResponse>> getMenu(Long categoryId){
        List<MenuResponse> menuResponses = menuService.getMenu(categoryId);
        return ResponseEntity.ok(menuResponses);
    }
}

