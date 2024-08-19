package com.stayforyou.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginTryRequest {

    private final String email;

    private final String password;
}
