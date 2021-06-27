package com.project.service.user.impl;

import com.project.repository.user.CategoryRepository;
import com.project.entity.Category;
import com.project.model.dto.CategoryDTO;
import com.project.model.mapper.CategoryMapper;
import com.project.service.user.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList= new ArrayList<>();
        for(Category c : categories){
            categoryDTOList.add(CategoryMapper.toCategoryDTO(c));
        }
        return categoryDTOList;
    }
}
