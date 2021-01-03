package com.challenge.markhashtags.security;

import com.challenge.markhashtags.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class JwtManager {

  public String createToken(User user) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, SecurityConstants.JWT_EXP_DAYS);
    return Jwts.builder()
            .setSubject(user.getEmail())
            .claim("id", user.getId())
            .setExpiration(calendar.getTime())
            .signWith(SignatureAlgorithm.HS512, SecurityConstants.API_KEY.getBytes())
            .compact();
  }

  public Claims parseToken(String jwt) throws JwtException {
    return Jwts.parser()
            .setSigningKey(SecurityConstants.API_KEY.getBytes())
            .parseClaimsJws(jwt)
            .getBody();
  }
}
