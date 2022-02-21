package com.serrano.app.forum.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class JWTProvider {
    private static final Algorithm ALGORITHM = Algorithm.HMAC256("9a&t+Gg?YC$9cK!E".getBytes());

    public static String create(User user, int minutes, String issuer, String claim){
        Date date = new Date(System.currentTimeMillis() + (minutes * 60000));
        String username = user.getUsername();
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(date)
                .withIssuer(issuer)
                .withClaim(claim, getClaimValues(user))
                .sign(ALGORITHM);
    }

    public static String create(com.serrano.app.forum.domain.User user, int minutes, String issuer, String claim, boolean domain){
        Date date = new Date(System.currentTimeMillis() + (minutes * 60000));
        String username = user.getEmail();
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(date)
                .withIssuer(issuer)
                .withClaim(claim, getClaimValues(user))
                .sign(ALGORITHM);
    }

    public static String create(User user, int minutes, String issuer){
        Date date = new Date(System.currentTimeMillis() + (minutes * 60000));
        String username = user.getUsername();
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(date)
                .withIssuer(issuer)
                .sign(ALGORITHM);
    }

    public static DecodedJWT decode(String token){
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        return verifier.verify(token);
    }

    public static UsernamePasswordAuthenticationToken createAuthenticationToken(DecodedJWT decoded){
        String username = decoded.getSubject();
        String[] roles = decoded.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        Stream.of(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }


    private static List<String> getClaimValues(User user){
        return user.getAuthorities().stream()
            .map(role -> role.getAuthority())
            .collect(Collectors.toList());
    }

    private static List<String> getClaimValues(com.serrano.app.forum.domain.User user){
        return user.getRoles().stream()
            .map(role -> role.getName().name())
            .collect(Collectors.toList());
    }
}