package com.sms.loginmodels;

import com.sms.entity.MccCodeMaster;
import java.util.List;

public class MccCodeResponse {
   private boolean status;
   private String message;
   private String respCode;
   private List<MccCodeMaster> data;

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

   public List<MccCodeMaster> getData() {
      return this.data;
   }

   public void setData(List<MccCodeMaster> data) {
      this.data = data;
   }
}
