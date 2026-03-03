package com.example.stock.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.http.Cookie;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException, ServletException {
        String jwt = getJwtFromRequest(request);
        if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            String username = jwtUtil.getUsernameFromToken(jwt);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Successfully authenticated user '" + username + "' from JWT.");
        } else if (StringUtils.hasText(jwt) && !jwtUtil.validateToken(jwt)) {
            logger.warn("JWT token found but validation failed for token: "
                    + jwt.substring(0, Math.min(jwt.length(), 20)) + "...");
        }

        chain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            logger.debug("JWT Token found in Authorization Header.");
            return bearerToken.substring(7);
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("session_id".equals(cookie.getName())) { // LoginFilter에서 설정한 쿠키 이름과 동일해야 함
                    logger.debug("JWT Token found in Cookie: " + cookie.getName());
                    return cookie.getValue();
                }
            }
        }
        logger.debug("No JWT Token found in Authorization Header or Cookies for request: " + request.getRequestURI());
        return null;
    }
}
