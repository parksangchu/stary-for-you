package com.stayforyou.common.exception;

import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getFieldError();

        String field = Objects.requireNonNull(fieldError).getField();
        String message = fieldError.getDefaultMessage();

        ErrorResult errorResult = ErrorResult.builder()
                .field(field)
                .message(message)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleBizException(BizException e) {
        ErrorResult errorResult = ErrorResult.builder()
                .message(e.getMessage())
                .build();

        if (e instanceof NotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorResult);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResult);
    }
}
