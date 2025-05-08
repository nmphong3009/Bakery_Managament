package com.example.Bakery.Management.System.Controller;

import com.example.Bakery.Management.System.DTOS.Request.SourceImageRequest;
import com.example.Bakery.Management.System.DTOS.Response.SourceImageResponse;
import com.example.Bakery.Management.System.Service.SourceImageService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("image")
public class ImageController {
    @Lazy
    private final SourceImageService sourceImageService;

    @PostMapping("admin/create")
    public ResponseEntity<?> create(@RequestBody SourceImageRequest request) {
        return ResponseEntity.ok(sourceImageService.create(request));
    }

    @PutMapping("admin/update")
    public ResponseEntity<?> update(@RequestBody SourceImageRequest request) {
        return ResponseEntity.ok(sourceImageService.update(request));
    }

    @DeleteMapping("admin/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(sourceImageService.delete(id));
    }

    @GetMapping("admin/get/{id}")
    public ResponseEntity<SourceImageResponse> get(@PathVariable Long id) {
        return sourceImageService.get(id);
    }

    @GetMapping("admin/getAll")
    public ResponseEntity<List<SourceImageResponse>> getAll() {
        List<SourceImageResponse> sourceImageResponses = sourceImageService.getAllImage();
        return ResponseEntity.ok(sourceImageResponses);
    }

}
