package com.web.Mlog.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SequenceGenerator(
        name = "CATEGORY_SEQ_GENERATOR"
        , sequenceName = "CATEGORY_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
public class Category {
    @Id
    @GeneratedValue
    private int id;
    @Column(length = 100)
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<Post> postList;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
