package com.sms.usermodel;

import java.util.HashMap;
import java.util.Map;

public class UserCommonResponse {
   private boolean status;
   private String message;
   private String respCode;
   private Map<String, Object> data = new HashMap();

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

   public Map<String, Object> getData() {
      return this.data;
   }

   public void setData(String name, Object value) {
      this.data.put(name, value);
   }
}
