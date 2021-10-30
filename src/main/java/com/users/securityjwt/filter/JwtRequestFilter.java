package com.users.securityjwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.users.securityjwt.service.MyUserDetails;
import com.users.securityjwt.util.JWTUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	@Autowired
	MyUserDetails userDetailsService;

	@Autowired
	JWTUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = "";
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			jwt = authHeader.substring(7);
			username = jwtUtil.extractUserName(jwt);
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if(jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				userPassAuthToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(userPassAuthToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
