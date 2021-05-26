package com.mamda.tp.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mamda.tp.SpringApplicationContext;
import com.mamda.tp.model.RefreshToken;
import com.mamda.tp.requestmodels.SingInRequest;

import com.mamda.tp.service.RefreshTokenService;
import com.sun.net.httpserver.Headers;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JWTProvider jwtProvider;

    private int count = 0;

    public AuthenticationFilter(AuthenticationManager authenticationManager,JWTProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
//            System.out.println("Attempt Connection Count : "+(++count));
            System.out.println("Ip Adress trying to attempt the connection : "+request.getRemoteAddr());
            System.out.println("Mac adress Trying to attempt connection : "+request.getRemoteHost());
            SingInRequest userInfo = new ObjectMapper().readValue(request.getInputStream(), SingInRequest.class);
            return authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userInfo.getEmail(), userInfo.getPassword()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        /**
         * The Logic handled is:
         * Create a RefreshToken using a refreshtokenService and add it To The Header With the Refresh-token
         */
        String email = ((User) authResult.getPrincipal()).getUsername();
        RefreshTokenService refreshTokenService = (RefreshTokenService) SpringApplicationContext.getBean("refreshTokenService");
        String token = jwtProvider.generateToken(email);
        RefreshToken refreshToken = refreshTokenService.generateRefreshTokenIfnotExistOrIsExpired(email);
        response.addHeader(SecurityCanstants.HEADER_STRING, SecurityCanstants.TOKEN_PREFIX + token);
        response.addHeader(SecurityCanstants.REFRESH_TOKEN_STRING, refreshToken.getToken());
        /**
         * This code if you want to pass token and refreshtoken in the cookie with the httponly attribute true
         */
//        Cookie tokenCookie = new Cookie(SecurityCanstants.HEADER_STRING,token);
//        tokenCookie.setHttpOnly(true);
//        tokenCookie.setMaxAge((int)SecurityCanstants.EXPIRATION_TIME);
//        Cookie refreshTokenCookie = new Cookie(SecurityCanstants.REFRESH_TOKEN_STRING,refreshToken.getToken());
//        refreshTokenCookie.setHttpOnly(true);
//        response.addCookie(tokenCookie);
//        response.addCookie(refreshTokenCookie);
        // response.addHeader("UserID", user.getId());
    }
}
