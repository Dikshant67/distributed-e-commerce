package com.ecommerce.product.service.impl;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final String uploadDir = System.getProperty("user.dir")+"/uploads/product/";

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

    @Override
    public ProductDto uploadImage(Long productId, MultipartFile file) throws IOException {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (file.isEmpty()) {
            throw new RuntimeException("Empty file");
        }

        long maxSize = 2 * 1024 * 1024;

        if (file.getSize() > maxSize) {
            throw new RuntimeException("File is too large");
        }

        Path uploadDir = Paths.get(
                System.getProperty("user.dir"),
                "upload",
                "products"
        );

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String fileName = UUID.randomUUID() + ".jpeg";

        Path filePath = uploadDir.resolve(fileName);

        log.info("Saving image to: {}", filePath.toAbsolutePath());

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        log.info("Image exists after save: {}", Files.exists(filePath));
        log.info("Image size after save: {} bytes", Files.size(filePath));

        product.setImageUrl(fileName);

        productRepository.save(product);

        return productMapper.toDto(product);
    }
}
