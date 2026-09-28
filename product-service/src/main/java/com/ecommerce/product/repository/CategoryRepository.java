package com.ecommerce.product.repository;

import com.ecommerce.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCategoryName(String categoryName);
    List<Category> findByParentId(Long categoryId);
    boolean existsByName(String name);
}
