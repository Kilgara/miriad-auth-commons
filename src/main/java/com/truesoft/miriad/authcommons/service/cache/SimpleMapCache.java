package com.truesoft.miriad.authcommons.service.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public class SimpleMapCache implements TokenCache {

    private final Map<String, UserDetails> userDetailsMap = new HashMap<>();

    @Override
    public void put(String token, UserDetails userDetails) {
        userDetailsMap.put(token, userDetails);
    }

    @Override
    public UserDetails get(String token) {
        return userDetailsMap.get(token);
    }

    @Override
    public void remove(String token) {
        userDetailsMap.remove(token);
    }
}
