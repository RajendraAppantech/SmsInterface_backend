package com.sms.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
   private String secret = "DDFFFdfhgfd335dbdfd";
   @Value("${SESSION_TIME}")
   private Integer sessionTime;
   private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

   public String generateToken(String username) {
      Map<String, Object> claims = new HashMap();
      return this.doGenerateToken(claims, username);
   }

   private String doGenerateToken(Map<String, Object> claims, String subject) {
      logger.info("Generating JWT Token for subject: {}", subject);
      String token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + (long)(this.sessionTime * 60 * 1000))).signWith(SignatureAlgorithm.HS512, this.secret).compact();
      return token;
   }

   public Boolean isTokenExpired(String token) {
      Date expiration = this.getExpirationDateFromToken(token);
      boolean isExpired = expiration.before(new Date());
      logger.info("JWT Token expiration check: {}", isExpired);
      return isExpired;
   }

   public Date getExpirationDateFromToken(String token) {
      return (Date)this.getClaimFromToken(token, Claims::getExpiration);
   }

   public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
      Claims claims = this.getAllClaimsFromToken(token);
      return claimsResolver.apply(claims);
   }

   private Claims getAllClaimsFromToken(String token) {
      try {
         return (Claims)Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
      } catch (ExpiredJwtException var3) {
         return var3.getClaims();
      }
   }

   public String refreshToken(String token) {
      Claims claims = this.getAllClaimsFromToken(token);
      return this.doGenerateToken(claims, claims.getSubject());
   }

   public Boolean validateToken(String token, String username) {
      String extractedUsername = this.getUsernameFromToken(token);
      boolean isValid = username.equals(extractedUsername) && !this.isTokenExpired(token);
      logger.info("JWT Token validation result for username {}: {}", username, isValid);
      return isValid;
   }

   public String getUsernameFromToken(String token) {
      return (String)this.getClaimFromToken(token, Claims::getSubject);
   }
}
