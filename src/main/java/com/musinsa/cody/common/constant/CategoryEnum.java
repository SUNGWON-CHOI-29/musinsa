package com.musinsa.cody.common.constant;

import com.musinsa.cody.common.exception.CodyException;

public enum CategoryEnum {

    TOP(1L, "상의"),
    OUTER(2L, "아우터"),
    PANTS(3L, "바지"),
    SNEAKERS(4L, "스니커즈"),
    BAG(5L, "가방"),
    CAP(6L, "모자"),
    SOCKS(7L, "양말"),
    ACCESSORY(8L, "액세서리");

    private final Long id;
    private final String name;

    CategoryEnum(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Long getIdByName(String name) {
        for (CategoryEnum category : CategoryEnum.values()) {
            if (category.getName().equals(name)) {
                return category.getId();
            }
        }
        throw new CodyException(CodyErrorResult.BRAND_NOT_FOUND);
    }
}
