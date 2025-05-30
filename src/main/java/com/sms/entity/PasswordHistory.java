package com.sms.entity;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@DynamicInsert
@DynamicUpdate
@Entity(name = "passwd_history")
@Table(name = "passwd_history")
@IdClass(PasswordHistoryId.class)
public class PasswordHistory {
	private String userid;
	private String password_type;
	private String old_passwd;
	private String new_passwd;
	private Date changetime;

	@Id
	@Column(name = "user_id", length = 100)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Id
	@Column(name = "passwd_type", length = 10)
	public String getPassword_type() {
		return this.password_type;
	}

	public void setPassword_type(String password_type) {
		this.password_type = password_type;
	}

	@Id
	@Column(name = "old_passwd", length = 10)
	public String getOld_passwd() {
		return this.old_passwd;
	}

	public void setOld_passwd(String old_passwd) {
		this.old_passwd = old_passwd;
	}

	@Id
	@Column(name = "new_passwd", length = 10)
	public String getNew_passwd() {
		return this.new_passwd;
	}

	public void setNew_passwd(String new_passwd) {
		this.new_passwd = new_passwd;
	}

	@Id
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "change_time", length = 23)
	public Date getChangetime() {
		return this.changetime;
	}

	public void setChangetime(Date changetime) {
		this.changetime = changetime;
	}

	public String toString() {
		return "PasswordHistory [userid=" + this.userid + ", password_type=" + this.password_type + ", old_passwd="
				+ this.old_passwd + ", new_passwd=" + this.new_passwd + ", changetime=" + this.changetime + "]";
	}
}
