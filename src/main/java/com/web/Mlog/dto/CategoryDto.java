package com.web.Mlog.dto;

import com.web.Mlog.domain.Category;
import lombok.*;

public class CategoryDto {
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryListDto {
        private int id;
        private String categoryName;
    }
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryAddDto {
        private String categoryName;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryDeleteDto {
        private String categoryName;
        public Category toEntity() {
            return new Category(this.categoryName);
        }
    }
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryModifyDto {
        private String categoryName;
        private String modifiedCategory;
    }
}
