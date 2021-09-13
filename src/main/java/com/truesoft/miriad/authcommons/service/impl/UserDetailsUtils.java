package com.truesoft.miriad.authcommons.service.impl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

class UserDetailsUtils {

    /**
     * Makes a copy of UserDetails object without password field
     * @param userDetails
     * @return
     */
    public static UserDetails copy(UserDetails userDetails) {
        return new UserDetails() {

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return new HashSet<>(userDetails.getAuthorities());
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return userDetails.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return userDetails.isAccountNonExpired();
            }

            @Override
            public boolean isAccountNonLocked() {
                return userDetails.isAccountNonLocked();
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return userDetails.isCredentialsNonExpired();
            }

            @Override
            public boolean isEnabled() {
                return userDetails.isEnabled();
            }
        };
    }
}
