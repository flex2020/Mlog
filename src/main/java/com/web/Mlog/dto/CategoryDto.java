package com.web.Mlog.dto;

import com.web.Mlog.domain.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryDto {
    private String categoryName;

    public Category toEntity() {
        return new Category(this.categoryName);
    }
}
