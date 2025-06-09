package com.sms.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.sms.entity.OurSmsMaster;
import com.sms.entity.SmsMaster;
import com.sms.entity.UserMaster;
import com.sms.loginmodels.ForgotPasswordRequest;
import com.sms.loginmodels.LoginResponse;
import com.sms.repositories.Repositories;
import com.sms.repositories.Repositories.OurSmsMasterRepository;

@Service
public class ForgotPasswordService {
   private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordService.class);
   private static final ThreadLocal<SimpleDateFormat> rrnSuffixFormat = ThreadLocal.withInitial(() -> {
      return new SimpleDateFormat("yyyyDDDHH");
   });
   private static final SecureRandom SECURE_RANDOM = new SecureRandom();
   
   @Autowired
   private Repositories.UserMasterRepository masterRepository;
   
   @Autowired
   private Repositories.SmsMasterRepository smsMasterRepository;
   
   @Autowired
   Repositories.PasswordExpRepository passwordExpRepository;
   
   @Autowired
   private OurSmsMasterRepository ourSmsMasterRepository;
   
   @Value("${SMS_KEY}")
   private String smsKey;
   
   @Value("${SMS_FROM}")
   private String smsFrom;
   
   @Value("${USER_CODE}")
   private String userCode;
   
   @Value("${sms.dlt.telemarketer.id}")
   private String dltTelemarketerId;

   public LoginResponse forgotPassword(ForgotPasswordRequest req) {
      logger.info("\r\n\r\n**************************** Forgot Passoword *************************************");
      LoginResponse response = new LoginResponse();

      try {
         UserMaster master = this.masterRepository.findByUserId(req.getUsername().toUpperCase());
         if (master == null) {
            response.setStatus(false);
            response.setMessage("Invalid username");
            response.setRespCode("01");
            return response;
         } else if (master.getAuthStatus().equalsIgnoreCase("3")) {
            response.setStatus(false);
            response.setMessage("User is not active.");
            response.setRespCode("01");
            return response;
         } else {
            String txnId = this.userCode + this.getTxnId();
            String otp = this.genrateOTP();
            
            OurSmsMaster sms = new OurSmsMaster();
			sms.setMobileNo(master.getMobileNo());
			sms.setUsername(userCode);
			sms.setOtp(otp);
			sms.setOtpDate(new Date());
			sms.setSms("Dear User, OTP for forgot password is " + otp + ". OTP is valid for 5 mins - Appan Dukan");
			sms.setStatus("P");
			sms.setSmsKey("");
			sms.setSmsFrom("ADMSPP");
			sms.setTemplateId("1707174892970824300");
			sms.setEntityId("1201160819143415278");
			sms.setSmsResponse("SMS send pending for proccess");
			sms.setSendTxnId(txnId);
			sms.setDlttelemarketerId(dltTelemarketerId);
			ourSmsMasterRepository.save(sms);
            
            
            response.setStatus(true);
            response.setMessage("OTP successfully sent on your registered mobile no : XXXXXX" + master.getMobileNo().substring(6, 10));
            response.setRespCode("00");
            return response;
         }
      } catch (Exception var7) {
         logger.info("EXCEPTION : " + var7);
         response.setStatus(false);
         response.setMessage("EXCEPTION");
         response.setRespCode("EX");
         return response;
      }
   }

   public LoginResponse getOtp() {
      logger.info("\r\n\r\n**************************** GET OTP *************************************");
      LoginResponse response = new LoginResponse();

      try {
    	  OurSmsMaster master = this.ourSmsMasterRepository.findTop1ByOrderBySmsidDesc();
         if (master == null) {
            response.setStatus(false);
            response.setMessage("Record not found");
            response.setRespCode("01");
            return response;
         } else {
            response.setStatus(true);
            response.setMessage(master.getSms());
            response.setRespCode("00");
            return response;
         }
      } catch (Exception var3) {
         logger.info("EXCEPTION : " + var3);
         response.setStatus(false);
         response.setMessage("EXCEPTION");
         response.setRespCode("EX");
         return response;
      }
   }

   public LoginResponse valOtp(SmsMaster req) {
      logger.info("\r\n\r\n**************************** VALIDATE OTP *************************************");
      LoginResponse response = new LoginResponse();
      if (!Strings.isNullOrEmpty(req.getUsername()) && !Strings.isNullOrEmpty(req.getOtp())) {
         try {
            UserMaster master = this.masterRepository.findByUserId(req.getUsername().toUpperCase());
            if (master == null) {
               response.setStatus(false);
               response.setMessage("Invalid Username");
               response.setRespCode("01");
               return response;
            } else {
               OurSmsMaster ms = this.ourSmsMasterRepository.findByMobileNoAndOtp(master.getMobileNo(), req.getOtp());
               if (ms == null) {
                  response.setStatus(false);
                  response.setMessage("WRONG OTP");
                  response.setRespCode("01");
                  return response;
               } else if (Strings.isNullOrEmpty(ms.getOtp())) {
                  response.setStatus(false);
                  response.setMessage("WRONG OTP");
                  return response;
               } else {
                  if (Integer.valueOf(this.findDateDiffInMin(ms.getOtpDate())) > 5) {
                     response.setStatus(false);
                     response.setMessage("OTP_EXPIRES");
                     response.setRespCode("01");
                  } else if (ms.getOtp().equalsIgnoreCase(req.getOtp())) {
                     response.setStatus(true);
                     response.setMessage("SUCCESS");
                     response.setRespCode("00");
                  } else {
                     response.setStatus(false);
                     response.setMessage("WRONG OTP");
                     response.setRespCode("01");
                  }

                  return response;
               }
            }
         } catch (Exception var5) {
            logger.info("EXCEPTION : " + var5);
            response.setStatus(false);
            response.setMessage("EXCEPTION");
            response.setRespCode("EX");
            return response;
         }
      } else {
         response.setStatus(false);
         response.setMessage("INVALID DETAILS");
         response.setRespCode("01");
         return response;
      }
   }

   public String genrateOTP() {
      StringBuilder generatedToken = new StringBuilder();

      try {
         SecureRandom number = SecureRandom.getInstance("SHA1PRNG");

         for(int i = 0; i < 6; ++i) {
            generatedToken.append(number.nextInt(9));
         }
      } catch (NoSuchAlgorithmException var4) {
         var4.printStackTrace();
      }

      return generatedToken.toString();
   }

   public String findDateDiffInMin(Date date) {
      try {
         long duration = (new Date()).getTime() - date.getTime();
         long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
         return "" + diffInMinutes;
      } catch (Exception var6) {
         return "0";
      }
   }

   public String getTxnId() {
      return ((SimpleDateFormat)rrnSuffixFormat.get()).format(new Date()).substring(3) + String.format("%06d", SECURE_RANDOM.nextInt(999999));
   }
}
