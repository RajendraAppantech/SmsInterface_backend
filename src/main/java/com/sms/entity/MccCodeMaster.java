package com.sms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(
   name = "mcc_code_master",
   schema = "sms_gateway"
)
public class MccCodeMaster {
   @Id
   @Column(
      name = "mcc",
      nullable = false,
      length = 10
   )
   private String mcc;
   @Column(
      name = "description",
      length = 255
   )
   private String description;
   @Column(
      name = "classification",
      length = 255
   )
   private String classification;
   @Column(
      name = "status",
      length = 10
   )
   private String status;
   @Column(
      name = "create_date"
   )
   private LocalDateTime createDate;

   public String getMcc() {
      return this.mcc;
   }

   public void setMcc(String mcc) {
      this.mcc = mcc;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getClassification() {
      return this.classification;
   }

   public void setClassification(String classification) {
      this.classification = classification;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public LocalDateTime getCreateDate() {
      return this.createDate;
   }

   public void setCreateDate(LocalDateTime createDate) {
      this.createDate = createDate;
   }
}
