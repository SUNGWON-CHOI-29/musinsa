package com.musinsa.cody.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.cody.entity.Product;
import com.musinsa.cody.util.FormatUtil;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PriceRangeResponse {

    @JsonProperty("카테고리")
    private String categoryName;

    @JsonProperty("최저가")
    private List<ProductDto> minProduct;

    @JsonProperty("최고가")
    private List<ProductDto> maxProduct;

    @Getter
    public static class ProductDto {

        public ProductDto(String brandName, String price) {
            this.brandName = brandName;
            this.price = price;
        }

        @JsonProperty("브랜드")
        private String brandName;
        @JsonProperty("가격")
        private String price;

        public static ProductDto fromEntity(Product product) {
            return new ProductDto(
                    product.getBrand().getName(),
                    FormatUtil.convertPrice(product.getPrice())
            );
        }
    }

}
