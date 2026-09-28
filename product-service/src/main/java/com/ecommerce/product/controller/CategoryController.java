package com.ecommerce.product.controller;

import com.ecommerce.product.dto.CategoryDto;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.repository.CategoryRepository;
import com.ecommerce.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor()
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id,@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.updateCategory(id,categoryDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok("Deleted category with id "+id);
    }
    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAll(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }




}
