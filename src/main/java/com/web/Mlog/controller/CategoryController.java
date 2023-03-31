package com.web.Mlog.controller;

import com.web.Mlog.dto.CategoryDto;
import com.web.Mlog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 카테고리 목록
     * */
    @GetMapping("")
    public List<CategoryDto.CategoryListDto> categoryList() {
        return categoryService.getCategoryList();
    }

    /**
     * 카테고리 추가
     * */
    @PostMapping("")
    public boolean categoryAdd(@RequestBody CategoryDto.CategoryAddDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    /**
     * 카테고리 삭제
     * */
    @DeleteMapping("")
    public boolean categoryDelete(@RequestBody CategoryDto.CategoryDeleteDto categoryDto) {
        return categoryService.deleteCategory(categoryDto);
    }

    /**
     * 카테고리 수정
     * */
    @PutMapping("")
    public boolean categoryModify(@RequestBody CategoryDto.CategoryModifyDto categoryDto) {
        return categoryService.modifyCategory(categoryDto);
    }
}
