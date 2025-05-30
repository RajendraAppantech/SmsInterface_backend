package com.sms.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "user_master")
//@EntityListeners({ AuditingEntityListener.class })
public class UserMaster {
	@Id
	@Column(name = "user_id", length = 50, nullable = false)
	private String userId;
	
	@Column(name = "name", length = 200)
	private String name;
	
	@Column(name = "mobile_no", length = 13)
	private String mobileNo;
	
	@Column(name = "email_id", length = 30)
	private String emailId;
	
	@Column(name = "user_role", length = 30)
	private String userRole;
	
	@Column(name = "user_profile", length = 30)
	private String userProfile;
	
	@Column(name = "status", length = 10)
	private String status;
	
	@Column(name = "user_code")
	private String userCode;
	
	@Column(name = "passwd", length = 64)
	private String passwd;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "passwd_exp")
	private Date passwdExp;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_dt")
	private Date lastLoginDt;
	
	@Column(name = "login_attempt")
	private Integer loginAttempt;
	
	@Column(name = "lock_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lockTime;
	
	@CreatedBy
	@Column(name = "created_by", length = 100)
	private String createdBy;
	
	@CreatedDate
	@Column(name = "created_dt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDt;
	
	@LastModifiedBy
	@Column(name = "modify_by", length = 100)
	private String modifyBy;
	
	@LastModifiedDate
	@Column(name = "modify_dt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDt;
	
	@Column(name = "auth_by", length = 100)
	private String authBy;
	
	@Column(name = "auth_dt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.TIMESTAMP)
	private Date authDt;
	
	@Column(name = "auth_status", length = 10)
	private String authStatus;
	
	@Column(name = "logout_status", length = 10)
	private String logoutStatus;
	
	@Column(name = "last_logout_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogoutDate;
	
	@Column(name = "user_menu", length = 100)
	private String userMenu;

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserRole() {
		return this.userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserProfile() {
		return this.userProfile;
	}

	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Date getPasswdExp() {
		return this.passwdExp;
	}

	public void setPasswdExp(Date passwdExp) {
		this.passwdExp = passwdExp;
	}

	public Date getLastLoginDt() {
		return this.lastLoginDt;
	}

	public void setLastLoginDt(Date lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
	}

	public Integer getLoginAttempt() {
		return this.loginAttempt;
	}

	public void setLoginAttempt(Integer loginAttempt) {
		this.loginAttempt = loginAttempt;
	}

	public Date getLockTime() {
		return this.lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDt() {
		return this.createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public String getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyDt() {
		return this.modifyDt;
	}

	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}

	public String getAuthBy() {
		return this.authBy;
	}

	public void setAuthBy(String authBy) {
		this.authBy = authBy;
	}

	public Date getAuthDt() {
		return this.authDt;
	}

	public void setAuthDt(Date authDt) {
		this.authDt = authDt;
	}

	public String getAuthStatus() {
		return this.authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getLogoutStatus() {
		return this.logoutStatus;
	}

	public void setLogoutStatus(String logoutStatus) {
		this.logoutStatus = logoutStatus;
	}

	public Date getLastLogoutDate() {
		return this.lastLogoutDate;
	}

	public void setLastLogoutDate(Date lastLogoutDate) {
		this.lastLogoutDate = lastLogoutDate;
	}

	public String getUserMenu() {
		return this.userMenu;
	}

	public void setUserMenu(String userMenu) {
		this.userMenu = userMenu;
	}

	public String toString() {
		return "UserMaster [userId=" + this.userId + ", name=" + this.name + ", mobileNo=" + this.mobileNo
				+ ", emailId=" + this.emailId + ", userRole=" + this.userRole + ", userProfile=" + this.userProfile
				+ ", status=" + this.status + ", userCode=" + this.userCode + ", passwd=" + this.passwd + ", passwdExp="
				+ this.passwdExp + ", lastLoginDt=" + this.lastLoginDt + ", loginAttempt=" + this.loginAttempt
				+ ", lockTime=" + this.lockTime + ", createdBy=" + this.createdBy + ", createdDt=" + this.createdDt
				+ ", modifyBy=" + this.modifyBy + ", modifyDt=" + this.modifyDt + ", authBy=" + this.authBy
				+ ", authDt=" + this.authDt + ", authStatus=" + this.authStatus + ", logoutStatus=" + this.logoutStatus
				+ ", lastLogoutDate=" + this.lastLogoutDate + ", userMenu=" + this.userMenu + "]";
	}
}
