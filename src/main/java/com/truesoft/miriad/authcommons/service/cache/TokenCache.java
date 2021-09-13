package com.truesoft.miriad.authcommons.service.cache;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenCache {

    /**
     * Adds UserDetails object to cache
     * @param token
     * @param userDetails
     */
    void put(String token, UserDetails userDetails);

    /**
     * Returns UserDetails object from cache
     * @param token
     * @return UserDetails object
     */
    UserDetails get(String token);

    /**
     * Remove UserDetails object from cache
     * @param token
     */
    void remove(String token);
}
