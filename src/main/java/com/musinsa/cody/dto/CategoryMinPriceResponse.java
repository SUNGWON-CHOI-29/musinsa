package com.musinsa.cody.dto;

import com.musinsa.cody.entity.Product;
import com.musinsa.cody.util.FormatUtil;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CategoryMinPriceResponse {
    private List<ProductDto> productList;
    private String totalPrice;

    @Getter
    public static class ProductDto {

        private ProductDto(String categoryName, String brandName, String price) {
            this.categoryName = categoryName;
            this.brandName = brandName;
            this.price = price;
        }

        private String categoryName;
        private String brandName;
        private String price;

        public static ProductDto fromEntity(Product product) {
            return new ProductDto(
                    product.getCategory().getName(),
                    product.getBrand().getName(),
                    FormatUtil.convertPrice(product.getPrice())
            );
        }
    }
}
