package com.web.Mlog.service;

import com.web.Mlog.domain.Category;
import com.web.Mlog.dto.CategoryDto;
import com.web.Mlog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<String> getCategoryList() {
        List<Category> all = categoryRepository.findAll();
        List<String> categoryList = new ArrayList<>();
        for (Category category : all) {
            categoryList.add(category.getCategoryName());
        }

        return categoryList;
    }

    public boolean addCategory(CategoryDto categoryDto) {
        // 카테고리가 이미 존재하는 경우
        System.out.println(categoryDto);
        if(categoryRepository.existsById(categoryDto.getCategoryName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 카테고리입니다.");
        }
        return categoryDto.getCategoryName().equals(categoryRepository.save(new Category(categoryDto.getCategoryName())).getCategoryName());
    }
}
