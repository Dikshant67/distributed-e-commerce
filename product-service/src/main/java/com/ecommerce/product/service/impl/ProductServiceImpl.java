package com.ecommerce.product.service.impl;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Category category = null;
        if(productDto.getCategoryId()!=null){
            category = new Category();
            category.setId(productDto.getCategoryId());
        }
      Product product=  productMapper.toEntity(productDto,category);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
       Product product= productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setDiscountPrice(productDto.getDiscountPrice());
        if(productDto.getCategoryId()!=null){
            Category category = new Category();
            category.setId(productDto.getCategoryId());
            product.setCategory(category);
        }
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto getProduct(Long id) {
      Product product=  productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
        return productMapper.toDto(product);
    }

    @Override
    public Page<ProductDto> getAllProducts(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> searchProduct(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.searchProductByKeyword(keyword, pageable);


        return products.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> filterProduct(Long categoryId, Double minPrice, Double maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.searchByAdvancedFilter(null, categoryId, minPrice, maxPrice, pageable);
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> advancedFilterProduct(Long categoryId, Double minPrice, Double maxPrice, int page, int size, String sortBy, String sortDirection) {
       Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size,sort);
        Page<Product> productPage = productRepository.searchByAdvancedFilter(null
                , categoryId, minPrice, maxPrice, pageable);
        return productPage.map(productMapper::toDto);
    }
}
