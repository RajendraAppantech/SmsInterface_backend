package com.sms.services;

import com.sms.entity.UserMaster;
import com.sms.jwt.JwtUtil;
import com.sms.jwt.MyUserDetailsService;
import com.sms.loginmodels.LoginRequest;
import com.sms.loginmodels.LoginResponse;
import com.sms.loginmodels.UserData;
import com.sms.repositories.Repositories;
import com.sms.utils.CommonUtils;
import com.sms.utils.DBUtils;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
   @Autowired
   private Repositories.UserMasterRepository masterRepository;
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
   private static final Logger Logger = LoggerFactory.getLogger(LoginService.class);

   public LoginResponse doLogin(LoginRequest req) {
      Logger.info("\r\n\r\n**************************** LOGIN *************************************");
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
                     response.setMessage("A new user has been found. Please set your password by clicking on 'Set Password' and try again.");
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
                     response.setMessage("You are already logged in. Please log out before attempting to log in again.");
                     response.setRespCode("01");
                     return response;
                  }

                  this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(master.getUserId().toUpperCase(), pass));
                  UserDetails userDetails = this.userDetailsService.loadUserByUsername(master.getUserId().toUpperCase());
                  String jwt = this.jwtUtil.generateToken(userDetails.getUsername());
                  this.masterRepository.updateLoginDetailsMaster("N", new Date(), master.getUserId());
                  UserData uData = new UserData();
                  uData.setName(master.getName());
                  uData.setUsername(master.getUserId());
                  uData.setMenu(master.getUserMenu());
                  uData.setToken("Bearer " + jwt);
                  uData.setLastLoginDate(lastLoginDt);
                  response.setData(uData);
                  response.setStatus(true);
                  response.setMessage("SUCCESS");
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
