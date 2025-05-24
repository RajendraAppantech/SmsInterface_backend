package com.sms.loginmodels;

public class LoginResponse {
   private boolean status;
   private String message;
   private String respCode;
   private UserData data;

   public boolean isStatus() {
      return this.status;
   }

   public void setStatus(boolean status) {
      this.status = status;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getRespCode() {
      return this.respCode;
   }

   public void setRespCode(String respCode) {
      this.respCode = respCode;
   }

   public UserData getData() {
      return this.data;
   }

   public void setData(UserData data) {
      this.data = data;
   }

   public String toString() {
      return "LoginResponse [status=" + this.status + ", message=" + this.message + ", respCode=" + this.respCode + ", data=" + this.data + "]";
   }
}
