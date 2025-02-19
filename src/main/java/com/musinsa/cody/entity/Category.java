package com.musinsa.cody.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Category extends Common {

    public Category(String name) {
        this.name = name;
    }

    @Column(unique = true)
    private String name;
}
