package com.project.model.mapper;

import com.project.entity.Category;
import com.project.model.dto.CategoryDTO;

public class CategoryMapper {
    public static CategoryDTO toCategoryDTO(Category category){
        return new CategoryDTO(category.getId(), category.getName());
    }
    public static Category toCategory(CategoryDTO categoryDTO){
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        return category;
    }
}
