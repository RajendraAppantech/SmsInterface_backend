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
   name = "pincode_master"
)
public class PincodeMaster {
   @Id
   @Column(
      name = "pin_code",
      nullable = false,
      length = 10
   )
   private String pinCode;
   @Column(
      name = "city_name",
      length = 100
   )
   private String cityName;
   @Column(
      name = "state",
      length = 30
   )
   private String state;
   @Column(
      name = "country",
      length = 30
   )
   private String country;
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

   public String getPinCode() {
      return this.pinCode;
   }

   public void setPinCode(String pinCode) {
      this.pinCode = pinCode;
   }

   public String getCityName() {
      return this.cityName;
   }

   public void setCityName(String cityName) {
      this.cityName = cityName;
   }

   public String getState() {
      return this.state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public String getCountry() {
      return this.country;
   }

   public void setCountry(String country) {
      this.country = country;
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
