package com.users.securityjwt.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

public class JWTUtil {

	private String SECRET_KEY = "secret";

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 60 * 1000))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		Date expiryDate = claims.getExpiration();
		String username = claims.getSubject();
		if (userDetails.getUsername().equals(username) && expiryDate.before(new Date())) {
			return true;
		}
		return false;
	}

}