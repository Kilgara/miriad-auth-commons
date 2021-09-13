package com.truesoft.miriad.authcommons.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.truesoft.miriad.authcommons.exception.InvalidJwtTokenException;

public interface AuthTokenService {

    /**
     * Generates token based on UserDetails entity
     * @param userDetails
     * @return
     */
    String generateToken(UserDetails userDetails);

    /**
     * Returns UserDetails entity if the token is valid
     * @param token
     * @return
     * @throws InvalidJwtTokenException if token is not valid
     */
    UserDetails getUserDetailsFromToken(String token);

    /**
     * Expires token
     * @param token
     */
    void expireToken(String token);
}
