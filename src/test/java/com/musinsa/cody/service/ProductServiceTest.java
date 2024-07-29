package com.musinsa.cody.service;

import com.musinsa.cody.common.constant.CodyErrorResult;
import com.musinsa.cody.common.exception.CodyException;
import com.musinsa.cody.dto.ProductListResponse;
import com.musinsa.cody.dto.ProductRequest;
import com.musinsa.cody.dto.ProductResponse;
import com.musinsa.cody.entity.Brand;
import com.musinsa.cody.entity.Category;
import com.musinsa.cody.entity.Product;
import com.musinsa.cody.repository.BrandRepository;
import com.musinsa.cody.repository.CategoryRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BrandRepository brandRepository;

    private ProductRequest request;

    private Category category;

    private Brand brand;

    private Product product;

    @BeforeEach
    public void beforeEach() {
        request = ProductRequest.builder()
                .categoryId(1L)
                .brandId(1L)
                .price(500L)
                .build();

        brand = new Brand("brandA");

        category = new Category("category");

        product = Product.builder()
                .brand(brand)
                .category(category)
                .price(1000L)
                .build();
    }

    @Test
    public void 상품생성실패_카테고리정보없음() {
        // given
        when(brandRepository.findById(1L))
                .thenReturn(Optional.of(brand));
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        // when
        CodyException exception = assertThrows(CodyException.class, () -> {
            productService.createProduct(request);
        });

        // then
        assertThat(exception.getCodyErrorResult()).isEqualTo(CodyErrorResult.CATEGORY_NOT_FOUND);
    }

    @Test
    public void 상품생성실패_브랜드정보없음() {
        // given
        when(brandRepository.findById(1L))
                .thenReturn(Optional.empty());

        // when
        CodyException exception = assertThrows(CodyException.class, () -> {
            productService.createProduct(request);
        });

        // then
        assertThat(exception.getCodyErrorResult()).isEqualTo(CodyErrorResult.BRAND_NOT_FOUND);
    }

    @Test
    public void 상품생성성공() {
        // given
        when(brandRepository.findById(1L))
                .thenReturn(Optional.of(brand));
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class)))
                .thenReturn(product);
        when(brandRepository.findByIdWithLock(any()))
                .thenReturn(Optional.of(brand));

        // when
        ProductResponse productResponse = productService.createProduct(request);

        // then
        assertThat(productResponse.getBrandName()).isEqualTo(brand.getName());
        assertThat(productResponse.getCategoryName()).isEqualTo(category.getName());
        assertThat(productResponse.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    public void 상품리스트조회성공() {
        // given
        when(productRepository.findAll())
                .thenReturn(List.of(product));

        // when
        ProductListResponse productListResponse = productService.getProducts();

        // then
        assertThat(productListResponse.getProductDtoList().size()).isEqualTo(1);
    }

    @Test
    public void 상품업데이트실패_상품정보없음() {
        // given
        when(productRepository.findByIdWithLock(1L))
                .thenReturn(Optional.empty());

        // when
        CodyException exception = assertThrows(CodyException.class, () -> {
            productService.updateProduct(1L, request);
        });

        // then
        assertThat(exception.getCodyErrorResult()).isEqualTo(CodyErrorResult.PRODUCT_NOT_FOUND);
    }

    @Test
    public void 상품업데이트실패_카테고리정보없음() {
        // given
        when(productRepository.findByIdWithLock(1L))
                .thenReturn(Optional.of(product));
        when(brandRepository.findById(1L))
                .thenReturn(Optional.of(brand));
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        // when
        CodyException exception = assertThrows(CodyException.class, () -> {
            productService.updateProduct(1L, request);
        });

        // then
        assertThat(exception.getCodyErrorResult()).isEqualTo(CodyErrorResult.CATEGORY_NOT_FOUND);
    }

    @Test
    public void 상품업데이트실패_브랜드정보없음() {
        // given
        when(productRepository.findByIdWithLock(1L))
                .thenReturn(Optional.of(product));
        when(brandRepository.findById(1L))
                .thenReturn(Optional.empty());

        // when
        CodyException exception = assertThrows(CodyException.class, () -> {
            productService.updateProduct(1L, request);
        });

        // then
        assertThat(exception.getCodyErrorResult()).isEqualTo(CodyErrorResult.BRAND_NOT_FOUND);
    }

    @Test
    public void 상품업데이트성공() {
        // given
        when(productRepository.findByIdWithLock(1L))
                .thenReturn(Optional.of(product));
        when(brandRepository.findById(1L))
                .thenReturn(Optional.of(brand));
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));
        when(brandRepository.findByIdWithLock(any()))
                .thenReturn(Optional.of(brand));

        // when
        ProductResponse productResponse = productService.updateProduct(1L, request);

        // then
        assertThat(productResponse.getPrice()).isEqualTo(500L);

    }

    @Test
    public void 상품삭제실패_상품정보없음() {
        // given
        when(productRepository.findByIdWithLock(1L))
                .thenReturn(Optional.empty());

        // when
        CodyException exception = assertThrows(CodyException.class, () -> {
            productService.deleteProduct(1L);
        });

        // then
        assertThat(exception.getCodyErrorResult()).isEqualTo(CodyErrorResult.PRODUCT_NOT_FOUND);
    }

    @Test
    public void 상품삭제성공() {
        // given
        when(productRepository.findByIdWithLock(1L))
                .thenReturn(Optional.of(product));
        when(brandRepository.findByIdWithLock(any()))
                .thenReturn(Optional.of(brand));

        // when
        productService.deleteProduct(1L);

        // then
        assertThat(product.isDeleted()).isTrue();
    }
}
