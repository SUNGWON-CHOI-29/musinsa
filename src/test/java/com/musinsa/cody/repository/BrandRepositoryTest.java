package com.musinsa.cody.repository;

import com.musinsa.cody.entity.Brand;
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
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void 브랜드생성성공() {
        // given
        Brand brandA = new Brand("brandA");
        // when
        Brand savedBrand = brandRepository.save(brandA);

        // then
        assertThat(savedBrand.getName()).isEqualTo(brandA.getName());
    }

    @Test
    public void 브랜드조회실패_데이터가없음() {
        // given

        // when
        Optional<Brand> brand = brandRepository.findById(1L);

        // then
        assertThat(brand.isPresent()).isFalse();
    }

    @Test
    public void 브랜드조회성공() {
        // given
        Brand brand = new Brand("brandA");

        Brand savedBrand = brandRepository.save(brand);

        // when
        Optional<Brand> findBrand = brandRepository.findById(savedBrand.getId());

        // then
        assertThat(findBrand.isPresent()).isTrue();
        assertThat(findBrand.get().getName()).isEqualTo(savedBrand.getName());

    }

    @Test
    public void 브랜드리스트조회성공() {
        // given
        Brand brandA = new Brand("brandA");
        Brand brandB = new Brand("brandB");

        brandRepository.save(brandA);
        brandRepository.save(brandB);

        // when
        List<Brand> brandList = brandRepository.findAll();

        // then
        assertThat(brandList.size()).isEqualTo(2);
    }
}
