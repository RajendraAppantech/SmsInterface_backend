package com.sms.jwt;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class BlacklistService {
   private Set<String> blacklistedTokens = new HashSet();

   public void blacklistToken(String token) {
      this.blacklistedTokens.add(token);
   }

   public boolean isTokenBlacklisted(String token) {
      return this.blacklistedTokens.contains(token);
   }
}
