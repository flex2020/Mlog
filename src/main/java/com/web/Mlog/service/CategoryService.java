package com.web.Mlog.service;

import com.web.Mlog.domain.Category;
import com.web.Mlog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public boolean addCategory(String categoryName) {
        // 카테고리가 이미 존재하는 경우
        System.out.println(categoryName);
        if(categoryRepository.existsById(categoryName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 카테고리입니다.");
        }
        return categoryName.equals(categoryRepository.save(new Category(categoryName)).getCategoryName());
    }
}
