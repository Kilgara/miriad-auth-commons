package com.truesoft.miriad.authcommons.utils;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class TokenUtils {

    public static Optional<String> getToken(HttpServletRequest request) {
        final String headerAuth = request.getHeader("Authorization");

        return getToken(headerAuth);
    }

    public static Optional<String> getToken(String header) {
        return StringUtils.hasText(header) && header.startsWith("Bearer ")
            ? Optional.of(header.substring(7))
            : Optional.empty();
    }
}
