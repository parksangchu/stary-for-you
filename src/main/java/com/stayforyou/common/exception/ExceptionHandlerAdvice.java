package com.stayforyou.common.exception;

import com.stayforyou.common.dto.ErrorResult;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getFieldError();

        String field = Objects.requireNonNull(fieldError).getField();
        String message = fieldError.getDefaultMessage();

        return ErrorResult.builder()
                .field(field)
                .message(message)
                .build();
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
