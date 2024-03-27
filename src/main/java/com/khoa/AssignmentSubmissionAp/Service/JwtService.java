package com.khoa.AssignmentSubmissionAp.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private static final String SECRET_KEY = "QrL3jZL+WQ/a9lARE0QxSD9e+gITRQ3PRzGSVwq4H1EYXku1EL6NfdccFmVDN8H6m6kzCpgCGuU+WyCkGqBnZdVa3xdBL/qvtSV4Z4OF0IBQ+OvbaM6CEAunZU0mL8VchJRaGE1OcvoHJTHJ1s97Qf+Pn1oHiTFF6+ALa9r212I+5kqDQj5d+OJgxA7egafmqqmf9UcmqSbDBEKySBKL5GTxEmn1saiwxrYul4IWRmOTPSJz/JRoB/1/Qxrkp3VatQi8RoNmemEoV5DE9dI/N7m0iD9oPdtOF/f3TDFYgpieFH/mBD+TM4esAVk2x99/ktO+8IYuqjFrSu0E5Xmahthmow4PpoSZOqWs86sM5EI=\n";

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		return (extractUsername(token).equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	public String generateToken(
			Map<String, Object> extraClaims,
			UserDetails userDetails
			) {
				return Jwts
						.builder()
						.setClaims(extraClaims)
						.setSubject(userDetails.getUsername())
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
						.signWith(getSignKey(), io.jsonwebtoken.SignatureAlgorithm.HS256)
						.compact()
						;
		
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
