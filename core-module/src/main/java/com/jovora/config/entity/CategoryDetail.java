package com.jovora.config.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category_detail")
public class CategoryDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long categoryId;
    private long tenantId;
    private Long parentCategoryId;
    private String categoryName;
}
