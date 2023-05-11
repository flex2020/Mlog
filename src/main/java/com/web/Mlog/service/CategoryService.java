package com.web.Mlog.service;

import com.web.Mlog.domain.Category;
import com.web.Mlog.dto.CategoryDto;
import com.web.Mlog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDto.CategoryListDto> getCategoryList() {
        return categoryRepository.findAll().stream()
                .map(Category::toCategoryListDto)
                .collect(Collectors.toList());
    }

    public boolean addCategory(CategoryDto.CategoryAddDto categoryDto) {
        if (categoryRepository.existsByCategoryName(categoryDto.getCategoryName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 카테고리입니다.");
        }
        return categoryDto.getCategoryName().equals(categoryRepository.save(new Category(categoryDto.getCategoryName())).getCategoryName());
    }

    public boolean deleteCategory(CategoryDto.CategoryDeleteDto categoryDto) {
        if (!categoryRepository.existsByCategoryName(categoryDto.getCategoryName())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다.");
        }
        try {
            categoryRepository.delete(categoryDto.toEntity());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "해당 카테고리와 관련된 포스트가 존재합니다.");
        }
        return true;
    }

    @Transactional
    public boolean modifyCategory(CategoryDto.CategoryModifyDto  categoryDto) {
        try {
            Category category = categoryRepository.findByCategoryName(categoryDto.getCategoryName())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."));
            category.setCategoryName(categoryDto.getModifiedCategory());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "카테고리 수정을 실패했습니다.");
        }
        return true;
    }
}
