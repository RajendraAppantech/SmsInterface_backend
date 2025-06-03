package com.sms.services;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sms.entity.SmsMaster;
import com.sms.entity.UserMaster;
import com.sms.jwt.JwtUtil;
import com.sms.jwt.MyUserDetailsService;
import com.sms.loginmodels.LoginRequest;
import com.sms.loginmodels.LoginResponse;
import com.sms.loginmodels.LoginValidateRequest;
import com.sms.loginmodels.UserData;
import com.sms.repositories.Repositories;
import com.sms.repositories.Repositories.SmsMasterRepository;
import com.sms.utils.CommonUtils;
import com.sms.utils.DBUtils;

@Service
public class LoginService {
	@Autowired
	private Repositories.UserMasterRepository masterRepository;

	@Value("${SMS_KEY}")
	private String smsKey;

	@Value("${SMS_FROM}")
	private String smsFrom;

	@Value("${USER_CODE}")
	private String userCode;

	@Autowired
	private DBUtils dbUtils;
	@Autowired
	private CommonUtils commonUtils;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private SmsMasterRepository smsMasterRepository;

	private static final Logger Logger = LoggerFactory.getLogger(LoginService.class);

	private static final ThreadLocal<SimpleDateFormat> rrnSuffixFormat = ThreadLocal
			.withInitial(() -> new SimpleDateFormat("yyyyDDDHH"));
	private static final SecureRandom SECURE_RANDOM = new SecureRandom();

	public LoginResponse doLogin(LoginRequest req) {
		Logger.info("\r\n\r\n**************************** LOGIN - initiate *************************************");
		LoginResponse response = new LoginResponse();

		try {
			String pass = this.commonUtils.hashSHA256(req.getUsername().toUpperCase().trim(), req.getPassword().trim());
			UserMaster master = this.masterRepository.findByUserId(req.getUsername().toUpperCase());
			if (master == null) {
				response.setStatus(false);
				response.setMessage("Invalid username and password.");
				response.setRespCode("01");
				return response;
			} else {
				Logger.info("pass : " + pass);
				Date lastLoginDt = master.getLastLoginDt();
				String count;
				if (!master.getPasswd().equalsIgnoreCase(pass)) {
					count = this.dbUtils.getCount(master, "count");
					if (count.equalsIgnoreCase("true")) {
						response.setStatus(false);
						response.setMessage("Invalid username and password.");
						response.setRespCode("03");
					} else if (count.equalsIgnoreCase("false")) {
						response.setStatus(false);
						response.setMessage("User Locked Please wait for 1 hour");
						response.setRespCode("03");
					} else if (count.startsWith("for")) {
						response.setStatus(false);
						response.setMessage("User is Locked Please wait " + count);
						response.setRespCode("03");
					}

					return response;
				} else {
					count = this.dbUtils.getCount(master, "login");
					if (count.startsWith("for")) {
						response.setStatus(false);
						response.setMessage("User is Locked Please wait " + count);
						response.setRespCode("03");
					} else {
						if (master.getAuthStatus().equalsIgnoreCase("2")) {
							response.setStatus(false);
							response.setMessage(
									"A new user has been found. Please set your password by clicking on 'Set Password' and try again.");
							response.setRespCode("02");
							return response;
						}

						if (!master.getAuthStatus().equalsIgnoreCase("1")) {
							response.setStatus(false);
							response.setMessage("User is not active.");
							response.setRespCode("01");
							return response;
						}

						if (master.getLogoutStatus() != null && !master.getLogoutStatus().equalsIgnoreCase("Y")) {
							response.setStatus(false);
							response.setMessage(
									"You are already logged in. Please log out before attempting to log in again.");
							response.setRespCode("01");
							return response;
						}

						this.authenticationManager.authenticate(
								new UsernamePasswordAuthenticationToken(master.getUserId().toUpperCase(), pass));
						UserDetails userDetails = this.userDetailsService
								.loadUserByUsername(master.getUserId().toUpperCase());
						String jwt = this.jwtUtil.generateToken(userDetails.getUsername());
//						this.masterRepository.updateLoginDetailsMaster("N", new Date(), master.getUserId());

						/*
						 * UserData uData = new UserData(); uData.setName(master.getName());
						 * uData.setUsername(master.getUserId()); uData.setMenu(master.getUserMenu());
						 * uData.setToken("Bearer " + jwt); uData.setLastLoginDate(lastLoginDt);
						 * uData.setUserRole(master.getUserRole()); response.setData(uData);
						 */

						String otp = generateOtp();

						String txnId = userCode + getTxnId();
						SmsMaster sms = new SmsMaster();
						sms.setMobileNo(master.getMobileNo());
						sms.setUsername(userCode);
						sms.setOtpDate(new Date());
						sms.setSms("Dear user, your OTP for login is " + otp
								+ ". Do not share it with anyone. - Team Appantech");
						sms.setOtp(otp);
						sms.setStatus("SEND");
						sms.setSmsKey(smsKey);
						sms.setSmsFrom(smsFrom);
						sms.setTemplateId("1707172992507471784");
						sms.setEntityId("1201160819143415278");
						sms.setSmsResponse("SMS send pending for proccess");
						sms.setSendTxnId(txnId);
						smsMasterRepository.save(sms);
						
						UserData data = new UserData();
						data.setMobileNo(master.getMobileNo());

						response.setStatus(true);
						response.setMessage("OTP has been sent to your registered mobile number: " + master.getMobileNo());
						response.setRespCode("00");

					}

					return response;
				}
			}
		} catch (Exception var10) {
			var10.printStackTrace();
			Logger.info("EXCEPTION : " + var10);
			response.setStatus(false);
			response.setMessage("Something went wrong. Please try again.");
			response.setRespCode("EX");
			return response;
		}
	}

	private String generateOtp() {
		Random rand = new Random();
		int otp = 100000 + rand.nextInt(900000);
		return String.valueOf(otp);
	}

	public String getTxnId() {
		return rrnSuffixFormat.get().format(new Date()).substring(3)
				+ String.format("%06d", SECURE_RANDOM.nextInt(999999));
	}

	public LoginResponse validateOtpLogin(LoginValidateRequest req) {
		Logger.info("\r\n\r\n**************************** LOGIN - validate *************************************");
		LoginResponse response = new LoginResponse();

		try {
			String pass = this.commonUtils.hashSHA256(req.getUsername().toUpperCase().trim(), req.getPassword().trim());
			UserMaster master = this.masterRepository.findByUserId(req.getUsername().toUpperCase());
			if (master == null) {
				response.setStatus(false);
				response.setMessage("Invalid username and password.");
				response.setRespCode("01");
				return response;

			} else {
				Logger.info("pass : " + pass);
				Date lastLoginDt = master.getLastLoginDt();
				String count;
				if (!master.getPasswd().equalsIgnoreCase(pass)) {
					count = this.dbUtils.getCount(master, "count");
					if (count.equalsIgnoreCase("true")) {
						response.setStatus(false);
						response.setMessage("Invalid username and password.");
						response.setRespCode("03");
					} else if (count.equalsIgnoreCase("false")) {
						response.setStatus(false);
						response.setMessage("User Locked Please wait for 1 hour");
						response.setRespCode("03");
					} else if (count.startsWith("for")) {
						response.setStatus(false);
						response.setMessage("User is Locked Please wait " + count);
						response.setRespCode("03");
					}

					return response;
				} else {

					SmsMaster latestOtpRecord = smsMasterRepository.findTopByMobileNoOrderByOtpDateDesc(master.getMobileNo());

					if (latestOtpRecord == null) {
						response.setStatus(false);
						response.setMessage("OTP not found. Please request a new OTP.");
						response.setRespCode("04");
						return response;
					}

					System.out.println("latestOtpRecord " + latestOtpRecord.getOtp());

					if (!req.getOtp().equals(latestOtpRecord.getOtp())) {
						response.setStatus(false);
						response.setMessage("Invalid OTP.");
						response.setRespCode("05");
						return response;
					}

					long otpAgeMillis = new Date().getTime() - latestOtpRecord.getOtpDate().getTime();
					long otpValidityMillis = 5 * 60 * 1000;
					if (otpAgeMillis > otpValidityMillis) {
						response.setStatus(false);
						response.setMessage("OTP expired. Please request a new OTP.");
						response.setRespCode("06");
						return response;
					}
					// ---------------------

					count = this.dbUtils.getCount(master, "login");
					if (count.startsWith("for")) {
						response.setStatus(false);
						response.setMessage("User is Locked Please wait " + count);
						response.setRespCode("03");
					} else {
						if (master.getAuthStatus().equalsIgnoreCase("2")) {
							response.setStatus(false);
							response.setMessage(
									"A new user has been found. Please set your password by clicking on 'Set Password' and try again.");
							response.setRespCode("02");
							return response;
						}

						if (!master.getAuthStatus().equalsIgnoreCase("1")) {
							response.setStatus(false);
							response.setMessage("User is not active.");
							response.setRespCode("01");
							return response;
						}

						if (master.getLogoutStatus() != null && !master.getLogoutStatus().equalsIgnoreCase("Y")) {
							response.setStatus(false);
							response.setMessage(
									"You are already logged in. Please log out before attempting to log in again.");
							response.setRespCode("01");
							return response;
						}

						this.authenticationManager.authenticate(
								new UsernamePasswordAuthenticationToken(master.getUserId().toUpperCase(), pass));
						UserDetails userDetails = this.userDetailsService
								.loadUserByUsername(master.getUserId().toUpperCase());
						String jwt = this.jwtUtil.generateToken(userDetails.getUsername());
						this.masterRepository.updateLoginDetailsMaster("N", new Date(), master.getUserId());

						UserData uData = new UserData();
						uData.setName(master.getName());
						uData.setUsername(master.getUserId());
						uData.setMenu(master.getUserMenu());
						uData.setToken("Bearer " + jwt);
						uData.setLastLoginDate(lastLoginDt);
						uData.setUserRole(master.getUserRole());
						response.setData(uData);
						
						// ✅ Set success status
						response.setStatus(true);
						response.setMessage("Login successful.");
						response.setRespCode("00");

					}

					
					return response;
				}
			}
		} catch (Exception var10) {
			var10.printStackTrace();
			Logger.info("EXCEPTION : " + var10);
			response.setStatus(false);
			response.setMessage("Something went wrong. Please try again.");
			response.setRespCode("EX");
			return response;
		}
	}
}