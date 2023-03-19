package com.web.Mlog.dto;

import com.web.Mlog.domain.Category;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private String categoryName;

    public Category toEntity() {
        return new Category(this.categoryName);
    }
}