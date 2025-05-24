package com.sms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(
   name = "user_menu"
)
public class UserMenu {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   @Column(
      name = "menu_id",
      nullable = false
   )
   private Long menuId;
   @Column(
      name = "user_role",
      length = 30
   )
   private String userRole;
   @Column(
      name = "user_profile",
      length = 30
   )
   private String userProfile;
   @Column(
      name = "menu",
      length = 100
   )
   private String menu;
   @Column(
      name = "status",
      length = 10
   )
   private String status;

   public Long getMenuId() {
      return this.menuId;
   }

   public void setMenuId(Long menuId) {
      this.menuId = menuId;
   }

   public String getUserRole() {
      return this.userRole;
   }

   public void setUserRole(String userRole) {
      this.userRole = userRole;
   }

   public String getMenu() {
      return this.menu;
   }

   public void setMenu(String menu) {
      this.menu = menu;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getUserProfile() {
      return this.userProfile;
   }

   public void setUserProfile(String userProfile) {
      this.userProfile = userProfile;
   }

   public String toString() {
      return "UserMenu [menuId=" + this.menuId + ", userRole=" + this.userRole + ", userProfile=" + this.userProfile + ", menu=" + this.menu + ", status=" + this.status + "]";
   }
}
