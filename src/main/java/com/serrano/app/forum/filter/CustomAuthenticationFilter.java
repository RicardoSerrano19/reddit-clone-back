package com.serrano.app.forum.filter;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.serrano.app.forum.utils.JWTProvider;
import com.serrano.app.forum.utils.AuthenticationMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;

    @Autowired
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
       String username = request.getParameter("username");
       String password = request.getParameter("password");
       log.info("Username is : {} and password is: {}", username, password);
       UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
       return authenticationManager.authenticate(authenticationToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        int minutesToExpire = 5; 
        String accessToken = JWTProvider.create(user, minutesToExpire, request.getRequestURL().toString(), "roles");
        String refreshToken = JWTProvider.create(user, minutesToExpire * 2, request.getRequestURL().toString(), "roles");
        AuthenticationMessage.onAuthenticationSuccessful(response, accessToken, "bearer", refreshToken, String.valueOf(JWTProvider.decode(accessToken).getExpiresAt().getTime()));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
    	AuthenticationMessage.onAuthenticationUnsuccessful(response, failed.getMessage());
    }
}