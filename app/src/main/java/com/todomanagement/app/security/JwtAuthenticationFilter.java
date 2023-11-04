package com.todomanagement.app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Execute Before Executing Spring security Filter
// Validate the jwt Token and provides user Details to spring Security Authentication

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Get Jwt Token from http request
        String token = getTokenFromRequest(request);

        //validate Token
        if(StringUtils.hasText(token) &&  jwtTokenProvider.validateJwtToken(token)){

            // get Username from token

            String username = jwtTokenProvider.getUserName(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                      userDetails,
                      null,
                      userDetails.getAuthorities()
                    );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        filterChain.doFilter(request,response);
    }


    private String getTokenFromRequest(HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7,bearerToken.length());

        }
        return null;
        }

}
