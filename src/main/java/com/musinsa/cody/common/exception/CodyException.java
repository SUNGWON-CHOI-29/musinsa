package com.musinsa.cody.common.exception;

import com.musinsa.cody.common.constant.CodyErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class CodyException extends RuntimeException {

    private final CodyErrorResult codyErrorResult;
}
