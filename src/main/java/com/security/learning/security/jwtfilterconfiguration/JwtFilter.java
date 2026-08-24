package com.security.learning.security.jwtfilterconfiguration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtToken jwtTokenService;

    /**
     * @param request -> This will get us the token via header Authorization.
     *                Token, is all we need to check the signed signature.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        System.out.println("token : "+token);
        /*
        Why is SecurityContextHolder authentication is required in the first place?
         */

        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        token = authorizationHeader.split(" ")[1];

        if(SecurityContextHolder.getContext().getAuthentication()==null){
            try {
                Claims claims = jwtTokenService.verifySignatureAndExtractClaims(token);
                System.out.println("Authentication is null");
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(claims.getSubject(), null, Collections.emptyList());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }catch (ExpiredJwtException e){
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Authentication :"+SecurityContextHolder.getContext().getAuthentication());

        filterChain.doFilter(request,response );
    }
}
