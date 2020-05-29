package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.UserPrincipal;
import com.example.demo.model.LoginViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.auth0.jwt.JWT;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.util.Date;

import com.auth0.jwt.algorithms.Algorithm;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	
	/*Trigger when we issue POST request to /login
	 * pass "username":"Harshesh","password":"Harshesh01"
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)  {
		
		LoginViewModel credentials = null ;
		try {
			credentials = new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			credentials.getUsername(),
			credentials.getPassword(),
			new ArrayList<>()
				);
		
		Authentication auth = authenticationManager.authenticate(authenticationToken);
		return auth;
				
	} 
	
	@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Grab principal
        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();

        // Create JWT Token
        String token = JWT.create()
        			   .withSubject(principal.getUsername())
        			   .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
        			   .sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));


        // Add token in response
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX +  token);
    }
	
}