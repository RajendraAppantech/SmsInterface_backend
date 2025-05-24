package com.sms.loginmodels;

import com.sms.entity.BankNameMaster;
import java.util.List;

public class BankNameResponse {
   private boolean status;
   private String message;
   private String respCode;
   private List<BankNameMaster> data;

   public boolean isStatus() {
      return this.status;
   }

   public void setStatus(boolean status) {
      this.status = status;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getRespCode() {
      return this.respCode;
   }

   public void setRespCode(String respCode) {
      this.respCode = respCode;
   }

   public List<BankNameMaster> getData() {
      return this.data;
   }

   public void setData(List<BankNameMaster> data) {
      this.data = data;
   }
}
