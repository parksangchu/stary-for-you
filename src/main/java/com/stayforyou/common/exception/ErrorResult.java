package com.stayforyou.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class ErrorResult {

    private final String field;

    private final String message;
}
