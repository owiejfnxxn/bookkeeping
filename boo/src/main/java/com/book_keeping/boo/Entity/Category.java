package com.book_keeping.boo.Entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_type")
    private String categoryType;

    @Column(name = "category_icon")
    private String categoryIcon;

    @Column(name = "user_id")
    private Integer userId;
}
