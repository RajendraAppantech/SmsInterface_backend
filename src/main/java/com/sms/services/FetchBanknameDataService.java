package com.sms.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.entity.BankNameMaster;
import com.sms.entity.MccCodeMaster;
import com.sms.loginmodels.BankNameResponse;
import com.sms.loginmodels.MccCodeResponse;
import com.sms.repositories.Repositories.BankNameMasterRepository;
import com.sms.repositories.Repositories.MccCodeMasterRepository;

@Service
public class FetchBanknameDataService {
   private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
   
   @Autowired
   private BankNameMasterRepository bankNameMasterRepository;

   @Autowired
   private MccCodeMasterRepository mccCodeMasterRepository;

   public BankNameResponse bankname() {
      logger.info("\r\n\r\n**************************** GET BANK NAME *************************************");
      BankNameResponse response = new BankNameResponse();

      List<BankNameMaster> nameMaster = bankNameMasterRepository.findAll();
      
      try {
         if (!nameMaster.isEmpty() && nameMaster != null) {
            response.setStatus(true);
            response.setMessage("SUCCESS");
            response.setRespCode("00");
            response.setData(nameMaster);
            return response;
         } else {
            response.setStatus(false);
            response.setMessage("Record not found");
            response.setRespCode("01");
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

   public MccCodeResponse getAllMccData() {
      logger.info("\r\n\r\n**************************** GET MCC CODE *************************************");
      MccCodeResponse response = new MccCodeResponse();

      List<MccCodeMaster> mccMaster = mccCodeMasterRepository.findAll();
      
      try {
         if (!mccMaster.isEmpty() && mccMaster != null) {
            response.setStatus(true);
            response.setMessage("SUCCESS");
            response.setRespCode("00");
            response.setData(mccMaster);
            return response;
         } else {
            response.setStatus(false);
            response.setMessage("Record not found");
            response.setRespCode("01");
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
}
