package com.sms.loginmodels;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdatePasswordRequest {
   @NotNull(
      message = "Username cannot be null"
   )
   @NotBlank(
      message = "Username cannot be Blank"
   )
   private String username;
   private String otp;
   @NotNull(
      message = "password cannot be null"
   )
   @NotBlank(
      message = "password cannot be Blank"
   )
   private String password;
   @NotNull(
      message = "confirm password cannot be null"
   )
   @NotBlank(
      message = "confirm password cannot be Blank"
   )
   private String confirmPassword;

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

   public String getConfirmPassword() {
      return this.confirmPassword;
   }

   public void setConfirmPassword(String confirmPassword) {
      this.confirmPassword = confirmPassword;
   }

   public String getOtp() {
      return this.otp;
   }

   public void setOtp(String otp) {
      this.otp = otp;
   }
}
