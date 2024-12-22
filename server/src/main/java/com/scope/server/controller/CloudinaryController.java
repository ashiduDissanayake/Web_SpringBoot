package com.scope.server.controller;

import com.scope.server.model.Image;
import com.scope.server.model.Product;
import com.scope.server.service.CloudinaryService;
import com.scope.server.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cloudinary")
@CrossOrigin(origins = "http://localhost:5173")
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;
    private final ImageService imageService;

    @Autowired
    public CloudinaryController(CloudinaryService cloudinaryService, ImageService imageService) {
        this.cloudinaryService = cloudinaryService;
        this.imageService = imageService;
    }

    // List all images
    @GetMapping("/list")
    public ResponseEntity<List<Image>> list() {
        List<Image> images = imageService.list();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    // Get image by ID
    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable int id) {
        Image image = imageService.getImageById(id);
        if (image != null) {
            return new ResponseEntity<>(image, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Upload an image
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, Object> uploadResult = cloudinaryService.upload(file);

            // Save image details to database
            Image image = new Image(
                    (String) uploadResult.get("original_filename"),
                    (String) uploadResult.get("secure_url"),
                    (String) uploadResult.get("public_id"),
                    (Product) uploadResult.get("product")
            );
            imageService.save(image);

            return new ResponseEntity<>(uploadResult, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(Map.of("error", "Failed to upload image: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete an image by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable int id) {
        if (!imageService.exists(id)) {
            return new ResponseEntity<>(Map.of("error", "Image not found"), HttpStatus.NOT_FOUND);
        }

        Image image = imageService.getImageById(id);
        try {
            Map<String, Object> deleteResult = cloudinaryService.delete(image.getImageId());

            // Remove image from database
            imageService.delete(id);

            return new ResponseEntity<>(deleteResult, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(Map.of("error", "Failed to delete image: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
