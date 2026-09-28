package com.ecommerce.product.controller;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.mapper.CategoryMapper;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.repository.CategoryRepository;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.CategoryService;
import com.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    private final ProductMapper productMapper;

    private final CategoryMapper categoryMapper;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted Successfully");
    }
    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }
    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam(defaultValue = "asc") String sortDirection) {

        return ResponseEntity.ok(productService.getAllProducts(page, size, sortBy, sortDirection));
    }
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> searchProducts(@RequestParam String keyword,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(productService.searchProduct(keyword, page, size));
    }
    @GetMapping("/filter")
    public ResponseEntity<Page<ProductDto>> filterProducts(@RequestParam(required = false) Long categoryId,
                                                           @RequestParam(required = false,defaultValue = "0") Double minPrice,
                                                           @RequestParam(required = false,defaultValue = "0") Double maxPrice,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(productService.filterProduct(categoryId, minPrice, maxPrice, page, size));

    }
    @GetMapping("/advancefilter")
    public ResponseEntity<Page<ProductDto>> advanceFilterProducts(@RequestParam(required = false) Long categoryId,
                                                                  @RequestParam(required = false,defaultValue = "0") Double minPrice,
                                                                  @RequestParam(required = false,defaultValue = "0") Double maxPrice,
                                                                  @RequestParam(required = false, defaultValue = "0") int page,
                                                                  @RequestParam(required = false, defaultValue = "10") int size,
                                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                                  @RequestParam(required = false, defaultValue = "asc") String sortDirection){
        return ResponseEntity.ok(productService.advancedFilterProduct(categoryId, minPrice, maxPrice, page, size,sortBy,sortDirection));

    }



}
