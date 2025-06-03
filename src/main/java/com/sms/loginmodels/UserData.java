package com.sms.loginmodels;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sms.DateSerializer;

@JsonIgnoreProperties
public class UserData {
	private String token;
	@JsonSerialize(using = DateSerializer.class)
	private Date lastLoginDate;
	private String menu;
	private String name;
	private String username;
	private String userRole;
	private String mobileNo;

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

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Override
	public String toString() {
		return "UserData [token=" + token + ", lastLoginDate=" + lastLoginDate + ", menu=" + menu + ", name=" + name
				+ ", username=" + username + ", userRole=" + userRole + ", getToken()=" + getToken()
				+ ", getLastLoginDate()=" + getLastLoginDate() + ", getMenu()=" + getMenu() + ", getName()=" + getName()
				+ ", getUsername()=" + getUsername() + ", getUserRole()=" + getUserRole() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
