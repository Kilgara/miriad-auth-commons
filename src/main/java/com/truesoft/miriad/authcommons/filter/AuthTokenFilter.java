package com.truesoft.miriad.authcommons.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.truesoft.miriad.authcommons.service.AuthTokenService;
import com.truesoft.miriad.authcommons.utils.TokenUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    private final AuthTokenService authTokenService;

    public AuthTokenFilter(AuthTokenService authTokenService) {
        this.authTokenService = authTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        catchExceptions(() -> {
            TokenUtils.getToken(request).ifPresent(token -> {
                final UserDetails userDetails = authTokenService.getUserDetailsFromToken(token);

                final UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
        });

        filterChain.doFilter(request, response);
    }

    private void catchExceptions(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            log.debug("Failed to authenticate user by token", e);
        }
    }
}
