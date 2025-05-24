package com.sms.loginmodels;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginRequest {
   @NotNull(
      message = "Username cannot be null"
   )
   @NotBlank(
      message = "Username cannot be Blank"
   )
   private String username;
   @NotNull(
      message = "Password cannot be null"
   )
   @NotBlank(
      message = "Password cannot be Blank"
   )
   private String password;

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}
