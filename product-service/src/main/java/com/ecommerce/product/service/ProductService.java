package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(Long id,ProductDto productDto);
    void deleteProduct(Long id);
    ProductDto getProduct(Long id);
    Page<ProductDto> getAllProducts(int page, int size,String sortBy,String sortDirection);
    Page<ProductDto> searchProduct(String keyword,int page,int size);
    Page<ProductDto> filterProduct(Long categoryId,Double minPrice,Double maxPrice, int page, int size);
    Page<ProductDto> advancedFilterProduct(Long categoryId,Double minPrice,Double maxPrice, int page, int size,String sortBy,String sortDirection);
    ProductDto uploadImage(Long productId, MultipartFile file) throws IOException;

}
