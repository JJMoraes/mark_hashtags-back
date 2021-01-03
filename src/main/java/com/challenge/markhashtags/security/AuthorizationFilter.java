package com.challenge.markhashtags.security;

import com.challenge.markhashtags.exception.Error;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;

public class AuthorizationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (jwt == null || !jwt.startsWith(SecurityConstants.JWT_PROVIDER)){
      Error error = new Error(HttpStatus.UNAUTHORIZED.value(), "Invalid JWT token", new Date(), null);
      PrintWriter writer = response.getWriter();

      ObjectMapper mapper = new ObjectMapper();
      String errorString = mapper.writeValueAsString(error);
      writer.write(errorString);

      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return;
    }
    jwt = jwt.replace("Bearer", "");
    try {
      Claims claims = new JwtManager().parseToken(jwt);
      String email = claims.getSubject();
      Authentication authentication =
              new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.createAuthorityList("ROLE_USER"));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception e) {
      Error error = new Error(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), new Date(), null);
      Writer writer = response.getWriter();

      ObjectMapper mapper = new ObjectMapper();
      String errorString = mapper.writeValueAsString(error);
      writer.write(errorString);

      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return;
    }
    filterChain.doFilter(request, response);
  }
}
