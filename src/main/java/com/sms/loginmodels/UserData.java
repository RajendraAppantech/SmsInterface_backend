package com.sms.loginmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sms.DateSerializer;
import java.util.Date;

@JsonIgnoreProperties
public class UserData {
   private String token;
   @JsonSerialize(
      using = DateSerializer.class
   )
   private Date lastLoginDate;
   private String menu;
   private String name;
   private String username;

   public String getToken() {
      return this.token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public Date getLastLoginDate() {
      return this.lastLoginDate;
   }

   public void setLastLoginDate(Date lastLoginDate) {
      this.lastLoginDate = lastLoginDate;
   }

   public String getMenu() {
      return this.menu;
   }

   public void setMenu(String menu) {
      this.menu = menu;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String toString() {
      return "UserData [token=" + this.token + ", lastLoginDate=" + this.lastLoginDate + ", menu=" + this.menu + ", name=" + this.name + ", username=" + this.username + "]";
   }
}
