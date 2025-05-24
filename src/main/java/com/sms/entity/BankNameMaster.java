package com.sms.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sms.DateSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(
   name = "bank_name_master",
   schema = "sms_gateway"
)
public class BankNameMaster {
   @Id
   @Column(
      name = "bank_name",
      nullable = false,
      length = 200
   )
   private String bankName;
   @Column(
      name = "status",
      length = 10
   )
   private String status;
   @JsonSerialize(
      using = DateSerializer.class
   )
   @Column(
      name = "create_date"
   )
   private Date createDate;

   public String getBankName() {
      return this.bankName;
   }

   public void setBankName(String bankName) {
      this.bankName = bankName;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public Date getCreateDate() {
      return this.createDate;
   }

   public void setCreateDate(Date createDate) {
      this.createDate = createDate;
   }
}
