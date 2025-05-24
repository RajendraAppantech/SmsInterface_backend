package com.sms.controller;

import com.sms.entity.SmsMaster;
import com.sms.loginmodels.BankNameResponse;
import com.sms.loginmodels.ForgotPasswordRequest;
import com.sms.loginmodels.LoginRequest;
import com.sms.loginmodels.LoginResponse;
import com.sms.loginmodels.MccCodeResponse;
import com.sms.loginmodels.PincodeResponse;
import com.sms.loginmodels.UpdatePasswordRequest;
import com.sms.repositories.Repositories;
import com.sms.services.FetchBanknameDataService;
import com.sms.services.FetchPincodedataService;
import com.sms.services.ForgotPasswordService;
import com.sms.services.LoginService;
import com.sms.services.UpdatePasswordService;
import jakarta.validation.Valid;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"sms"})
@Validated
public class SmsController {
   @Autowired
   private LoginService loginService;
   @Autowired
   private ForgotPasswordService forgotPasswordService;
   @Autowired
   private FetchBanknameDataService banknameDataService;
   @Autowired
   private FetchPincodedataService fetchPincodedataService;
   @Autowired
   private UpdatePasswordService updatePasswordService;
   @Autowired
   private Repositories.UserMasterRepository masterRepository;

   @PostMapping({"/login"})
   public LoginResponse doLogin(@Valid @RequestBody LoginRequest req) {
      return this.loginService.doLogin(req);
   }

   @GetMapping({"/getotp"})
   public LoginResponse getOtp() {
      return this.forgotPasswordService.getOtp();
   }

   @PostMapping({"/forgotPassword"})
   public LoginResponse forgotPassword(@Valid @RequestBody ForgotPasswordRequest req) {
      return this.forgotPasswordService.forgotPassword(req);
   }

   @PostMapping({"/valotp"})
   public LoginResponse valOtp(@RequestBody SmsMaster req) {
      return this.forgotPasswordService.valOtp(req);
   }

   @PostMapping({"/updatePassword"})
   public LoginResponse updatePassword(@Valid @RequestBody UpdatePasswordRequest req) {
      return this.updatePasswordService.updatePassword(req);
   }

   @GetMapping({"/bankname"})
   public BankNameResponse bankname() {
      return this.banknameDataService.bankname();
   }

   @GetMapping({"/pincode/{code}"})
   public PincodeResponse pincode(@PathVariable("code") String pincode) {
      return this.fetchPincodedataService.pincode(pincode);
   }

   @GetMapping({"/mcc"})
   public MccCodeResponse getAllMccData() {
      return this.banknameDataService.getAllMccData();
   }

   @GetMapping({"/updateLogoutStatus/{username}"})
   public MccCodeResponse updateLogoutStatus(@PathVariable("username") String username) {
      MccCodeResponse resp = new MccCodeResponse();
      this.masterRepository.updateLogOutDetailsMaster("Y", new Date(), username.toUpperCase());
      resp.setStatus(true);
      resp.setMessage("success");
      return resp;
   }
}
