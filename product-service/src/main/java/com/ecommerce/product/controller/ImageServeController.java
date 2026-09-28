package com.ecommerce.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Slf4j
public class ImageServeController {
    private final Path uploadDir =
            Paths.get(System.getProperty("user.dir"), "upload", "products");
    @GetMapping("/products/images/{fileName}")
    public ResponseEntity<Resource> getImage(
            @PathVariable String fileName) throws IOException {

        Path path = uploadDir.resolve(fileName).normalize();

        log.info("Looking for image at: {}", path.toAbsolutePath());

        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            log.warn("File not found or not readable: {}", path);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
