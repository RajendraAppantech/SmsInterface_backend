package com.sms.jwt;

import com.sms.repositories.Repositories;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Scope("singleton")
public class JwtRequestFilter extends OncePerRequestFilter {
   @Autowired
   private JwtUtil jwtTokenUtil;
   @Autowired
   private MyUserDetailsService userDetailsService;
   @Autowired
   private BlacklistService blacklistService;
   @Autowired
   private Repositories.UserMasterRepository masterRepository;
   private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
   public String username = null;
   public String jwtToken = null;
   public String newToken = null;

   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
      String requestTokenHeader = request.getHeader("authtoken");
      logger.info("\r\n\r\nrequestTokenHeader : {}", requestTokenHeader);
      String requestHeaders = (String)Collections.list(request.getHeaderNames()).stream().map((headerName) -> {
         return headerName + ": " + request.getHeader(headerName);
      }).reduce("", (acc, header) -> {
         return acc + header + ", ";
      });
      logger.info("Incoming request: {} {} ", request.getMethod(), request.getRequestURI());
      if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
         this.jwtToken = requestTokenHeader.substring(7);
         if (this.jwtToken.split("\\.").length == 3) {
            try {
               logger.info("requestHeaders.contains(authtoken) : {}", requestHeaders.contains("authtoken"));
               this.username = this.jwtTokenUtil.getUsernameFromToken(this.jwtToken);
               logger.info("JWT Token extracted, username: {}", this.username);
            } catch (IllegalArgumentException var8) {
               logger.error("Unable to get JWT Token", var8);
            } catch (ExpiredJwtException var9) {
               logger.error("JWT Token has expired", var9);
               this.masterRepository.updateLogOutDetailsMaster("Y", new Date(), this.username.toUpperCase());
               response.setStatus(401);
               logger.info("Returning 401 Unauthorized due to expired token");
               return;
            }
         } else {
            logger.error("JWT Token format is incorrect");
            this.masterRepository.updateLogOutDetailsMaster("Y", new Date(), this.username.toUpperCase());
         }
      } else {
         logger.warn("JWT Token does not begin with Bearer String");
      }

      if (!request.getRequestURI().contains("/sms/") && this.username != null && this.jwtToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
         ResponseEntity errorResponse;
         if (this.jwtTokenUtil.isTokenExpired(this.jwtToken)) {
            logger.info("JWT Token is expired, returning 401 Unauthorized");
            this.masterRepository.updateLogOutDetailsMaster("Y", new Date(), this.username.toUpperCase());
            errorResponse = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token is expired");
            response.setStatus(errorResponse.getStatusCodeValue());
            response.setContentType("text/plain");
            response.getWriter().write((String)errorResponse.getBody());
            return;
         }

         if (this.blacklistService.isTokenBlacklisted(this.jwtToken) || !this.jwtTokenUtil.validateToken(this.jwtToken, this.username)) {
            this.masterRepository.updateLogOutDetailsMaster("Y", new Date(), this.username.toUpperCase());
            logger.info("JWT Token is invalid, returning 403 FORBIDDEN");
            errorResponse = ResponseEntity.status(HttpStatus.FORBIDDEN).body("JWT token is invalid");
            response.setStatus(errorResponse.getStatusCodeValue());
            response.setContentType("text/plain");
            response.getWriter().write((String)errorResponse.getBody());
            return;
         }

         UserDetails userDetails = this.userDetailsService.loadUserByUsername(this.username);
         UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, (Object)null, userDetails.getAuthorities());
         usernamePasswordAuthenticationToken.setDetails((new WebAuthenticationDetailsSource()).buildDetails(request));
         SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
         this.blacklistService.blacklistToken(this.jwtToken);
         this.newToken = this.jwtTokenUtil.refreshToken(this.jwtToken);
         logger.info("OLD jwtToken : " + this.jwtToken);
         logger.info("NEW jwtToken : " + this.newToken);

         while(this.jwtToken.equalsIgnoreCase(this.newToken)) {
            logger.info("Same Token Was generated");
            this.blacklistService.blacklistToken(this.newToken);
            this.newToken = this.jwtTokenUtil.refreshToken(this.newToken);
         }

         logger.info("NEW1 jwtToken : " + this.newToken);
         response.setHeader("authtoken", "Bearer " + this.newToken);
         logger.info("JWT Token refreshed and added to response And Exsiting Token BlackListed...");
      }

      chain.doFilter(request, response);
      logger.info("Request processing completed");
   }

   @Bean
   public AuditorAware<String> auditorAware() {
      return new JwtRequestFilter.AuditorAwareImpl();
   }

   @Bean
   CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      configuration.setAllowCredentials(true);
      configuration.setAllowedOriginPatterns(Arrays.asList("*"));
      configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
      configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization", "authtoken"));
      configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Hash, Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authtoken"));
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
   }

   @Bean
   public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
      FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean(new CorsFilter(this.corsConfigurationSource()), new ServletRegistrationBean[0]);
      bean.setOrder(Integer.MIN_VALUE);
      return bean;
   }

   class AuditorAwareImpl implements AuditorAware<String> {
      public Optional<String> getCurrentAuditor() {
         return Optional.of(JwtRequestFilter.this.username);
      }
   }
}
