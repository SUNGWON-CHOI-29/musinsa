package com.musinsa.cody.service;

import com.musinsa.cody.common.constant.CodyErrorResult;
import com.musinsa.cody.common.exception.CodyException;
import com.musinsa.cody.dto.BrandListResponse;
import com.musinsa.cody.dto.BrandResponse;
import com.musinsa.cody.entity.Brand;
import com.musinsa.cody.repository.BrandRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

    @InjectMocks
    private BrandService brandService;

    @Mock
    private BrandRepository brandRepository;

    @Test
    public void 브랜드생성실패_중복된이름() {
        // given
        doThrow(DataIntegrityViolationException.class)
                .when(brandRepository).save(any(Brand.class));
        
        // when
        CodyException exception = assertThrows(CodyException.class, () -> {
            brandService.createBrand("brandName");
        });

        // then
        assertThat(exception.getCodyErrorResult()).isEqualTo(CodyErrorResult.DUPLICATED_BRAND_NAME);
    }

    @Test
    public void 브랜드생성성공() {
        // given
        when(brandRepository.save(any(Brand.class)))
                .thenReturn(new Brand("brandA"));

        // when
        BrandResponse response = brandService.createBrand("brandA");

        // then
        assertThat(response.getName()).isEqualTo("brandA");
    }

    @Test
    public void 브랜드리스트조회성공() {
        // given
        when(brandRepository.findAll())
                .thenReturn(Arrays.asList(
                                new Brand("brandA"),
                                new Brand("brandB")
                        ));
        // when
        BrandListResponse brands = brandService.getBrands();
        // then
        assertThat(brands.getBrandDtoList().size()).isEqualTo(2);
    }

    @Test
    public void 브랜드이름수정실패_브랜드정보없음() {
        // given
        when(brandRepository.findByName("brandA"))
                .thenReturn(Optional.empty());
        when(brandRepository.findById(1L))
                .thenReturn(Optional.empty());


        // when
        CodyException exception = assertThrows(CodyException.class, () -> {
            brandService.updateBrandName(1L,"brandA");
        });

        // then
        assertThat(exception.getCodyErrorResult()).isEqualTo(CodyErrorResult.BRAND_NOT_FOUND);
    }

    @Test
    public void 브랜드이름수정실패_브랜드명중복() {
        // given
        when(brandRepository.findByName("brandA"))
                .thenReturn(Optional.of(new Brand("brandA")));

        // when
        CodyException exception = assertThrows(CodyException.class, () -> {
            brandService.updateBrandName(1L,"brandA");
        });

        // then
        assertThat(exception.getCodyErrorResult()).isEqualTo(CodyErrorResult.DUPLICATED_BRAND_NAME);
    }

    @Test
    public void 브랜드이름수정성공() {
        // given
        when(brandRepository.findByName("brandA"))
                .thenReturn(Optional.empty());
        when(brandRepository.findById(1L))
                .thenReturn(Optional.of(new Brand("brand")));


        // when
        BrandResponse response = brandService.updateBrandName(1L, "brandA");

        // then
        assertThat(response.getName()).isEqualTo("brandA");
    }

    @Test
    public void 브랜드삭제실패_브랜드정보없음() {
        // given
        when(brandRepository.findById(1L))
                .thenReturn(Optional.empty());


        // when
        CodyException exception = assertThrows(CodyException.class, () -> {
            brandService.deleteBrand(1L);
        });

        // then
        assertThat(exception.getCodyErrorResult()).isEqualTo(CodyErrorResult.BRAND_NOT_FOUND);
    }

    @Test
    public void 브랜드삭제성공() {
        // given
        Brand brand = new Brand("brandA");
        when(brandRepository.findById(1L))
                .thenReturn(Optional.of(brand));

        // when
        brandService.deleteBrand(1L);

        // then
        assertThat(brand.isDeleted()).isTrue();
    }
}
