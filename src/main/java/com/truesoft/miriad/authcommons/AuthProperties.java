package com.truesoft.miriad.authcommons;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Data
public class AuthProperties {

    @Value("${jwt.expiration-ms}")
    private int expirationTime;

    @Value("${jwt.secret}")
    private String secret;
}
