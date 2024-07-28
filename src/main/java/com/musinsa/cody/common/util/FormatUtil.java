package com.musinsa.cody.common.util;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtil {
    public static String convertPrice(Long price) {
        return NumberFormat.getNumberInstance(Locale.KOREA).format(price);
    }
}
