package com.stayforyou.common.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RequiredArgsConstructor
public class ErrorResultUtil {

    public static void sendErrorResponse(HttpServletResponse response, HttpStatus httpStatus, String message,
                                         ObjectMapper objectMapper)
            throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        String content = makeErrorContent(message, objectMapper);

        response.getWriter()
                .write(content);
    }

    private static String makeErrorContent(String message, ObjectMapper objectMapper) throws JsonProcessingException {
        ErrorResult errorResult = ErrorResult.builder()
                .message(message)
                .build();

        return objectMapper.writeValueAsString(errorResult);
    }
}
