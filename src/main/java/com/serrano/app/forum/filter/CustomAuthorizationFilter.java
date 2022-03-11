package com.serrano.app.forum.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.serrano.app.forum.utils.AuthenticationMessage;
import com.serrano.app.forum.utils.JWTProvider;

public class CustomAuthorizationFilter extends OncePerRequestFilter{
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        //Dont apply filter for specific paths
        if(request.getServletPath().equals("/api/auth/login")){
            filterChain.doFilter(request, response);
            return;
        }
        
        //Get authorization header
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        //Check if there is not authorization or token is Bearer type
        if(!(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))){
            filterChain.doFilter(request, response);
            return;
        }

        try{
            String token = authorizationHeader.substring("Bearer ".length());
            DecodedJWT decoded = JWTProvider.decode(token);
            UsernamePasswordAuthenticationToken authenticationToken = JWTProvider.createAuthenticationToken(decoded);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        }catch(Exception ex){
            AuthenticationMessage.onError(response, ex.getMessage(), HttpStatus.FORBIDDEN);
        }       
    }
    
}
