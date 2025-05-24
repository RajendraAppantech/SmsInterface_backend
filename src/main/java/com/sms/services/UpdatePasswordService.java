package com.sms.services;

import com.google.common.base.Strings;
import com.sms.entity.PasswordHistory;
import com.sms.entity.SmsMaster;
import com.sms.entity.UserMaster;
import com.sms.loginmodels.LoginResponse;
import com.sms.loginmodels.UpdatePasswordRequest;
import com.sms.repositories.Repositories;
import com.sms.utils.CommonUtils;
import com.sms.utils.DBUtils;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdatePasswordService {
   private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordService.class);
   @Autowired
   private CommonUtils commonUtils;
   @Autowired
   private Repositories.UserMasterRepository masterRepository;
   @Autowired
   private Repositories.SmsMasterRepository smsMasterRepository;
   @Autowired
   private DBUtils dbUtils;
   @Autowired
   Repositories.PasswordExpRepository passwordExpRepository;

   @Transactional
   public LoginResponse updatePassword(UpdatePasswordRequest req) {
      logger.info("\r\n\r\n**************************** UPDATE PASSWORD *************************************");
      LoginResponse response = new LoginResponse();
      if (!Strings.isNullOrEmpty(req.getUsername()) && !Strings.isNullOrEmpty(req.getPassword()) && !Strings.isNullOrEmpty(req.getConfirmPassword())) {
         try {
            UserMaster master = this.masterRepository.findByUserId(req.getUsername().toUpperCase());
            if (master == null) {
               response.setStatus(false);
               response.setMessage("Invalid username");
               response.setRespCode("01");
               return response;
            } else {
               if (!Strings.isNullOrEmpty(req.getOtp())) {
                  SmsMaster ms = this.smsMasterRepository.findByMobileNoAndOtp(master.getMobileNo().toUpperCase(), req.getOtp());
                  if (ms == null) {
                     response.setStatus(false);
                     response.setMessage("WRONG OTP");
                     response.setRespCode("01");
                     return response;
                  }

                  if (Strings.isNullOrEmpty(ms.getOtp())) {
                     response.setStatus(false);
                     response.setMessage("WRONG OTP");
                     return response;
                  }

                  if (!ms.getOtp().equalsIgnoreCase(req.getOtp())) {
                     response.setStatus(false);
                     response.setMessage("WRONG OTP");
                     response.setRespCode("01");
                     return response;
                  }
               }

               if (!req.getPassword().equalsIgnoreCase(req.getConfirmPassword())) {
                  response.setStatus(false);
                  response.setMessage("New password and confirm password must be the same.");
                  response.setRespCode("01");
                  return response;
               } else {
                  String pass = this.commonUtils.hashSHA256(req.getUsername().toUpperCase(), req.getPassword());
                  boolean passStatus = this.dbUtils.getLastThreePassword(req.getUsername().toUpperCase(), pass);
                  if (!passStatus) {
                     response.setStatus(false);
                     response.setMessage("Your new password shoud not be same as last 3 Passwords.");
                     response.setRespCode("01");
                     return response;
                  } else {
                     Date passExDt = this.dbUtils.getExpiryDt();
                     PasswordHistory passhisty = new PasswordHistory();
                     passhisty.setUserid(req.getUsername().toUpperCase());
                     passhisty.setPassword_type("USER");
                     passhisty.setOld_passwd(master.getPasswd());
                     passhisty.setNew_passwd(pass);
                     passhisty.setChangetime(new Date());
                     this.passwordExpRepository.save(passhisty);
                     this.masterRepository.updatePasswordMaster(pass, passExDt, master.getUserId());
                     response.setStatus(true);
                     response.setMessage("Password changed successfully");
                     response.setRespCode("00");
                     return response;
                  }
               }
            }
         } catch (Exception var8) {
            var8.printStackTrace();
            logger.info("EXCEPTION : " + var8);
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
}
