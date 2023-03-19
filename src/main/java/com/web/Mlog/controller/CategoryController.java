package com.web.Mlog.controller;

import com.web.Mlog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 카테고리 추가
     * */
    @PostMapping("/{categoryName}")
    public boolean addCategory(@PathVariable String categoryName) {
        return categoryService.addCategory(categoryName);
    }

}
