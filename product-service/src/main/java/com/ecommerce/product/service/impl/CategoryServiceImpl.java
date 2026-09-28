package com.ecommerce.product.service.impl;

import com.ecommerce.product.dto.CategoryDto;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.mapper.CategoryMapper;
import com.ecommerce.product.repository.CategoryRepository;
import com.ecommerce.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(Long id , CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("category not found"));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setParentId(categoryDto.getParentId());
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {

        return categoryRepository.findById(categoryId).map(categoryMapper::toDto).orElseThrow(()-> new RuntimeException("category not found"));
    }

    @Override
    public List<CategoryDto> getAllCategories() {

        return categoryRepository.findAll().stream().
                map(categoryMapper::toDto).toList();
    }
}
