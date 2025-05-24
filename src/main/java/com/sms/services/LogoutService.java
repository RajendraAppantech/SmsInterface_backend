package com.sms.services;

import com.sms.entity.UserMaster;
import com.sms.loginmodels.ForgotPasswordRequest;
import com.sms.loginmodels.LoginResponse;
import com.sms.repositories.Repositories;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {
   @Autowired
   private Repositories.UserMasterRepository masterRepository;
   private static final Logger Logger = LoggerFactory.getLogger(LogoutService.class);

   public LoginResponse logout(ForgotPasswordRequest req) {
      Logger.info("\r\n\r\n**************************** LOGOUT *************************************");
      LoginResponse response = new LoginResponse();

      try {
         UserMaster master = this.masterRepository.findByUserId(req.getUsername().toUpperCase());
         if (master == null) {
            response.setStatus(false);
            response.setMessage("Username not found");
            response.setRespCode("01");
            return response;
         } else {
            master.setLogoutStatus("Y");
            master.setLastLogoutDate(new Date());
            this.masterRepository.save(master);
            response.setStatus(true);
            response.setMessage("User successfully logout.");
            response.setRespCode("00");
            return response;
         }
      } catch (Exception var4) {
         var4.printStackTrace();
         Logger.info("EXCEPTION : " + var4);
         response.setStatus(false);
         response.setMessage("Something went wrong. Please try again.");
         response.setRespCode("EX");
         return response;
      }
   }
}
