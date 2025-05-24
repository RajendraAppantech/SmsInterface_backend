package com.sms.services;

import com.sms.entity.PincodeMaster;
import com.sms.loginmodels.PincodeResponse;
import com.sms.repositories.Repositories;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchPincodedataService {
   private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
   @Autowired
   private Repositories.PincodeMasterRepository pincodeMasterRepository;

   public PincodeResponse pincode(String pincode) {
      logger.info("\r\n\r\n**************************** GET PINCODE DATA *************************************");
      PincodeResponse response = new PincodeResponse();

      try {
         List<PincodeMaster> master = this.pincodeMasterRepository.findByPinCode(pincode);
         if (!master.isEmpty() && master != null) {
            response.setStatus(true);
            response.setMessage("SUCCESS");
            response.setRespCode("00");
            response.setData(master);
            return response;
         } else {
            response.setStatus(false);
            response.setMessage("Record not found");
            response.setRespCode("01");
            return response;
         }
      } catch (Exception var4) {
         logger.info("EXCEPTION : " + var4);
         response.setStatus(false);
         response.setMessage("EXCEPTION");
         response.setRespCode("EX");
         return response;
      }
   }
}
