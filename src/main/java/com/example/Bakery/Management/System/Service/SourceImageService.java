package com.example.Bakery.Management.System.Service;

import com.example.Bakery.Management.System.DTOS.Request.SourceImageRequest;
import com.example.Bakery.Management.System.DTOS.Response.SourceImageResponse;
import com.example.Bakery.Management.System.Entity.SourceImage;
import com.example.Bakery.Management.System.Repository.SourceImageRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceImageService {
    @Lazy
    private final SourceImageRepository sourceImageRepository;

    @Lazy
    private final UserService userService;

    public SourceImageService(SourceImageRepository sourceImageRepository, UserService userService) {
        this.sourceImageRepository = sourceImageRepository;
        this.userService = userService;
    }

    public ResponseEntity<?> create(SourceImageRequest request) {
        if (!userService.isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        if (sourceImageRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("SourceImage already exists!");
        }

        SourceImage sourceImage = SourceImage.builder()
                .name(request.getName())
                .url(request.getUrl())
                .build();
        sourceImageRepository.save(sourceImage);
        return ResponseEntity.ok("Create Image successful");
    }

    public ResponseEntity<?> update(SourceImageRequest request) {
        if (!userService.isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        SourceImage sourceImage = sourceImageRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + request.getId()));
        sourceImage.setName(request.getName());
        sourceImage.setUrl(request.getUrl());
        sourceImageRepository.save(sourceImage);
        return ResponseEntity.ok("Update Image successful");
    }

    public ResponseEntity<?> delete(Long id) {
        if (!userService.isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        SourceImage sourceImage = sourceImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));
        sourceImageRepository.delete(sourceImage);
        return ResponseEntity.ok("Delete Image successful");
    }

    public List<SourceImageResponse> getAllImage() {
        if (!userService.isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        List<SourceImage> sourceImages = sourceImageRepository.findAll();
        return sourceImages.stream().map(
                image -> SourceImageResponse.builder()
                        .id(image.getId())
                        .url(image.getUrl())
                        .name(image.getName())
                        .build()
        ).toList();
    }

    public ResponseEntity<SourceImageResponse> get(Long id) {
        if (!userService.isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        SourceImage sourceImage = sourceImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));
        return new ResponseEntity<>(SourceImageResponse.builder()
                .id(sourceImage.getId())
                .url(sourceImage.getUrl())
                .name(sourceImage.getName())
                .build(), HttpStatus.OK);
    }
}
