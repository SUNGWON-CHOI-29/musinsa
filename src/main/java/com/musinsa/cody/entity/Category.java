package com.musinsa.cody.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Category extends Common{

    @Builder
    public Category(String name) {
        this.name = name;
    }

    @Column(unique = true)
    private String name;
}
