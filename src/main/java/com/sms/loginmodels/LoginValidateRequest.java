package com.sms.loginmodels;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginValidateRequest {
	
	@NotNull(message = "Username cannot be null")
	@NotBlank(message = "Username cannot be Blank")
	private String username;
	
	@NotNull(message = "Password cannot be null")
	@NotBlank(message = "Password cannot be Blank")
	private String password;
	
	@NotNull(message = "OTP cannot be null")
	@NotBlank(message = "OTP cannot be Blank")
	private String otp;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

}