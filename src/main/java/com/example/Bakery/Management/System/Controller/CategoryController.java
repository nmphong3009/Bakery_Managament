package com.example.Bakery.Management.System.Controller;

import com.example.Bakery.Management.System.DTOS.Request.CategoryRequest;
import com.example.Bakery.Management.System.DTOS.Request.MenuRequest;
import com.example.Bakery.Management.System.DTOS.Response.CategoryResponse;
import com.example.Bakery.Management.System.DTOS.Response.UserResponse;
import com.example.Bakery.Management.System.Service.CategoryService;
import com.example.Bakery.Management.System.Service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("category")
public class CategoryController {
    @Lazy
    private final CategoryService categoryService;

    @PostMapping("admin/create")
    public ResponseEntity<?> create (@RequestBody CategoryRequest request){
        return ResponseEntity.ok(categoryService.create(request));
    }

    @PutMapping("admin/update")
    public ResponseEntity<?> update (@RequestBody CategoryRequest request){
        return ResponseEntity.ok(categoryService.update(request));
    }

    @DeleteMapping("admin/delete/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @GetMapping("getAll")
    public ResponseEntity<List<CategoryResponse>> getAll(){
        List<CategoryResponse> categoryResponses = categoryService.getAllCategory();
        return ResponseEntity.ok(categoryResponses);
    }

}
