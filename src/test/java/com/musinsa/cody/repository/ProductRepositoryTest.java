package com.musinsa.cody.repository;

import com.musinsa.cody.entity.Brand;
import com.musinsa.cody.entity.Category;
import com.musinsa.cody.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.sql.init.mode=never"
})
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Brand brand;
    private Category category;

    @BeforeEach
    void setUp() {
        brand = new Brand("brandName");
        brand = brandRepository.save(brand);

        category = new Category("categoryName");
        category = categoryRepository.save(category);
    }

    @Test
    public void 상품생성성공() {
        // given
        Product product = Product.builder()
                .category(category)
                .brand(brand)
                .price(1000L)
                .build();

        // when
        Product saveProduct = productRepository.save(product);

        // then
        assertThat(saveProduct.getBrand().getName()).isEqualTo("brandName");
        assertThat(saveProduct.getCategory().getName()).isEqualTo("categoryName");
        assertThat(saveProduct.getPrice()).isEqualTo(1000L);
    }

    @Test
    public void 싱픔조회실패() {
        // given

        // when
        Optional<Product> findProduct = productRepository.findById(1L);

        // then
        assertThat(findProduct.isPresent()).isFalse();
    }

    @Test
    public void 상품조회성공() {
        // given
        Product product = Product.builder()
                .category(category)
                .brand(brand)
                .price(1000L)
                .build();

        Product savedProduct = productRepository.save(product);

        // when
        Optional<Product> findProduct = productRepository.findById(savedProduct.getId());

        // then
        assertThat(findProduct.isPresent()).isTrue();
    }

    @Test
    public void 상품리스트조회성공() {
        // given
        Product productA = Product.builder()
                .category(category)
                .brand(brand)
                .price(1000L)
                .build();

        Product productB = Product.builder()
                .category(category)
                .brand(brand)
                .price(1000L)
                .build();

        productRepository.save(productA);
        productRepository.save(productB);

        // when
        List<Product> productList = productRepository.findAll();

        // then
        assertThat(productList.size()).isEqualTo(2);
    }
}
