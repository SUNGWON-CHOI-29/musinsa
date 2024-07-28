package com.musinsa.cody.dto;

import com.musinsa.cody.entity.Product;
import lombok.Getter;

@Getter
public class ProductResponse {

    public ProductResponse(Long id, String categoryName, String brandName, Long price) {
        this.id = id;
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.price = price;
    }

    private Long id;
    private String categoryName;
    private String brandName;
    private Long price;

    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getCategory().getName(),
                product.getBrand().getName(),
                product.getPrice()
        );
    }
}
