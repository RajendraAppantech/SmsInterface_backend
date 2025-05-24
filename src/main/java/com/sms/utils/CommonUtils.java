package com.sms.utils;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;

@Service
public class CommonUtils {
   public String hashSHA256(String username, String password) {
      return Hashing.sha256().hashString(username + password + "smsgateway", StandardCharsets.UTF_8).toString();
   }

   public static void main(String[] args) {
      System.out.println("Password : " + Hashing.sha256().hashString("HARSHALAHarshala1@1234smsgateway", StandardCharsets.UTF_8).toString());
   }
}
