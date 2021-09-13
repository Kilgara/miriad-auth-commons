package com.truesoft.miriad.authcommons.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import com.truesoft.miriad.authcommons.exception.InvalidJwtTokenException;
import com.truesoft.miriad.authcommons.service.AuthTokenService;
import com.truesoft.miriad.authcommons.service.cache.TokenCache;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class AuthTokenServiceImpl implements AuthTokenService {

    private final int expirationTimeMs;
    private final String secret;
    private final TokenCache cache;

    public AuthTokenServiceImpl(int expirationTimeMs, String secret, TokenCache cache) {
        this.expirationTimeMs = expirationTimeMs;
        this.secret = secret;
        this.cache = cache;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        final String token = Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + expirationTimeMs))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();

        cacheTokenAndUserDetails(token, userDetails);

        return token;
    }

    @Override
    public UserDetails getUserDetailsFromToken(String token) {
        isValid(token);

        return Optional.ofNullable(cache.get(token))
            .orElseThrow(() -> new InvalidJwtTokenException("JWT token is expired"));
    }

    @Override
    public void expireToken(String token) {
        cache.remove(token);
    }

    private void cacheTokenAndUserDetails(String token, UserDetails userDetails) {
        cache.put(token, UserDetailsUtils.copy(userDetails));
    }

    private void isValid(String token) {
        try {
            validate(token);
        } catch (InvalidJwtTokenException e) {
            cache.remove(token);
            throw e;
        }
    }

    private void validate(String token) {
        try {
            Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token);
        } catch (SignatureException e) {
            throw new InvalidJwtTokenException("Invalid JWT signature", e);
        } catch (MalformedJwtException e) {
            throw new InvalidJwtTokenException("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            throw new InvalidJwtTokenException("JWT token is expired", e);
        } catch (UnsupportedJwtException e) {
            throw new InvalidJwtTokenException("JWT token is unsupported", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidJwtTokenException("JWT claims string is empty", e);
        }
    }
}
