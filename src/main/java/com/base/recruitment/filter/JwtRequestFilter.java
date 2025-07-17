package com.base.recruitment.filter;

import com.base.recruitment.service.jwt.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final String BEARER_WORD = "Bearer ";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        Optional<String> usernameOpt = Optional.empty();
        String jwt = null;
        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith(BEARER_WORD)) {
            jwt = authorizationHeader.replace(BEARER_WORD, "").trim();
            usernameOpt = Optional.ofNullable(tokenService.extractUsername(jwt));
        }

        if(usernameOpt.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
            String username = usernameOpt.get();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (tokenService.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
