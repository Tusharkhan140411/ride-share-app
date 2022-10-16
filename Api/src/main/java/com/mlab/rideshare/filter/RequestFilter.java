package com.mlab.rideshare.filter;

import com.mlab.rideshare.props.ApplicationProperties;
import com.mlab.rideshare.service.auth.AuthService;
import com.mlab.rideshare.util.jwt.JWTUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestFilter extends OncePerRequestFilter {
    @Autowired
    @Lazy
    private AuthService authService;

    private final ApplicationProperties props;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader(props.getAuthHeaderName());
        if (!JWTUtils.isTokenFormatValid(authToken) || JWTUtils.isTokenInvalidOrExpired(authToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Claims claims = JWTUtils.extractAllClaims(authToken);

            List<Map<String, String>> authorities = (List<Map<String, String>>) claims.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(item -> new SimpleGrantedAuthority(item.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(),
                    null,
                    simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("{} Triggered for URI {}", this.getClass().getName(), request.getRequestURI());

            filterChain.doFilter(request, response);
        } catch (JwtException ex) {
            throw new IllegalStateException("Token is not valid");
        }
    }
}
