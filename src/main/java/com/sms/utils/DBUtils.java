package com.sms.utils;

import com.sms.entity.PasswordHistory;
import com.sms.entity.UserMaster;
import com.sms.loginmodels.LoginResponse;
import com.sms.repositories.Repositories;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBUtils {
   @Autowired
   private Repositories.UserMasterRepository masterRepository;
   @Autowired
   private Repositories.PasswordExpRepository passwordExpRepository;

   public LoginResponse validateUserAndMobile(String userName, String mobileNo) {
      LoginResponse response = new LoginResponse();

      try {
         UserMaster master = this.masterRepository.findByUserId(userName.toUpperCase());
         if (master != null) {
            response.setStatus(false);
            response.setMessage("user already exist");
            response.setRespCode("01");
            return response;
         } else {
            if (!mobileNo.isEmpty()) {
               UserMaster master1 = this.masterRepository.findByMobileNo(mobileNo);
               if (master1 != null) {
                  response.setStatus(false);
                  response.setMessage("mobile no already exist");
                  response.setRespCode("01");
                  return response;
               }
            }

            response.setStatus(true);
            response.setMessage("SUCCESS");
            response.setRespCode("00");
            return response;
         }
      } catch (Exception var6) {
         var6.printStackTrace();
         response.setStatus(false);
         response.setMessage("Something went wrong. Please try again.");
         response.setRespCode("EX");
         return response;
      }
   }

   public String getCount(UserMaster master, String level) {
      try {
         if (master.getLoginAttempt() == null) {
            master.setLoginAttempt(0);
         }

         Date previous_time;
         long sec;
         long min;
         Date time;
         long diff;
         if (level.equalsIgnoreCase("login")) {
            if ((master.getLoginAttempt() == 3 || master.getLoginAttempt() > 3) && master.getLockTime().after(new Date())) {
               previous_time = master.getLockTime();
               time = new Date();
               diff = previous_time.getTime() - time.getTime();
               sec = diff / 1000L;
               min = sec / 60L;
               return "for " + min + " Minutes";
            } else {
               this.masterRepository.updateUnloackPeroid(0, (Date)null, new Date(), master.getUserId());
               return "true";
            }
         } else if ((master.getLoginAttempt() == 3 || master.getLoginAttempt() > 3) && master.getLockTime().after(new Date())) {
            previous_time = master.getLockTime();
            time = new Date();
            diff = previous_time.getTime() - time.getTime();
            sec = diff / 1000L;
            min = sec / 60L;
            return "for " + min + " Minutes";
         } else if (master.getLoginAttempt() != 3 && master.getLoginAttempt() <= 3) {
            previous_time = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(previous_time);
            calendar.add(10, 1);
            previous_time = calendar.getTime();
            Integer abc = master.getLoginAttempt() + 1;
            this.masterRepository.updateLockingPeroid(abc, previous_time, master.getUserId());
            return "true";
         } else {
            return "false";
         }
      } catch (Exception var11) {
         System.out.println("===============4=============");
         var11.printStackTrace();
         return "false";
      }
   }

   public boolean getLastThreePassword(String username, String newpass) {
      try {
         List<PasswordHistory> lstpassword = this.passwordExpRepository.fetchPasswordAndOldPasswd(username, newpass);
         System.out.println("lstpassword.size() : " + lstpassword.size());
         return lstpassword.size() < 3;
      } catch (Exception var4) {
         var4.printStackTrace();
         return false;
      }
   }

   public Date getExpiryDt() {
      try {
         int expiryDays = 60;
         Calendar cal = Calendar.getInstance();
         cal.setTime(new Date());
         cal.add(5, expiryDays);
         Date exDate = cal.getTime();
         return exDate;
      } catch (Exception var4) {
         var4.printStackTrace();
         return null;
      }
   }
}
