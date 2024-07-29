package com.musinsa.cody.service;

import com.musinsa.cody.common.util.FormatUtil;
import com.musinsa.cody.dto.BrandMinPriceResponse;
import com.musinsa.cody.dto.CategoryMinPriceResponse;
import com.musinsa.cody.dto.PriceRangeResponse;
import com.musinsa.cody.entity.Brand;
import com.musinsa.cody.entity.Category;
import com.musinsa.cody.entity.Product;
import com.musinsa.cody.repository.BrandRepository;
import com.musinsa.cody.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CodyServiceTest {

    @InjectMocks
    private CodyService codyService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    private Category category;

    private Brand brand;

    private Product minProduct;

    private Product maxProduct;

    @BeforeEach
    public void beforeEach() {
        brand = new Brand("brandA");

        category = new Category("category");

        minProduct = Product.builder()
                .brand(brand)
                .category(category)
                .price(1000L)
                .build();

        maxProduct = Product.builder()
                .brand(brand)
                .category(category)
                .price(5000L)
                .build();
    }

    @Test
    public void 카테고리별최저가조회_성공() {
        // given
        when(productRepository.findAllMinProductGroupByCategory())
                .thenReturn(List.of(minProduct, maxProduct));
        String totalPrice = FormatUtil.convertPrice(
                minProduct.getPrice() + maxProduct.getPrice()
        );

        // when
        CategoryMinPriceResponse response = codyService.getCategoryMinPriceProducts();

        // then
        assertThat(response.getProductList().size()).isEqualTo(2);
        assertThat(response.getTotalPrice()).isEqualTo(totalPrice);
    }

    @Test
    public void 최저가브랜드조회_성공() {
        // given
        when(brandRepository.findMinTotalPriceBrand())
                .thenReturn(Optional.of(brand));
        when(productRepository.findAllMinProductByBrandAndGroupByCategory(any()))
                .thenReturn(List.of(minProduct, maxProduct));
        String totalPrice = FormatUtil.convertPrice(
                minProduct.getPrice() + maxProduct.getPrice()
        );

        // when
        BrandMinPriceResponse brandMinPriceProducts = codyService.getBrandMinPriceProducts();
        BrandMinPriceResponse.BrandMinPrice brandMinPrice = brandMinPriceProducts.getBrandMinPrice();

        // then
        assertThat(brandMinPrice.getBrandName()).isEqualTo(brand.getName());
        assertThat(brandMinPrice.getTotal()).isEqualTo(totalPrice);
    }

    @Test
    public void 카테고리최고최저상품조회_성공() {
        // given
        when(productRepository.findByMinPriceProductByCategory(any()))
                .thenReturn(Optional.of(minProduct));
        when(productRepository.findByMaxPriceProductByCategory(any()))
                .thenReturn(Optional.of(maxProduct));

        // when
        PriceRangeResponse priceRangeResponse = codyService.getCategoryPriceRangeProducts(1L);

        // then
        assertThat(priceRangeResponse.getCategoryName()).isEqualTo(category.getName());
        assertThat(priceRangeResponse.getMinProduct().size()).isEqualTo(1);
        assertThat(priceRangeResponse.getMaxProduct().size()).isEqualTo(1);
    }
}
