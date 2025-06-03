package com.sms.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sms.entity.SmsMaster;
import com.sms.loginmodels.BankNameResponse;
import com.sms.loginmodels.ForgotPasswordRequest;
import com.sms.loginmodels.LoginInitiateRequest;
import com.sms.loginmodels.LoginRequest;
import com.sms.loginmodels.LoginResponse;
import com.sms.loginmodels.LoginValidateRequest;
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

@RestController
@RequestMapping({ "sms" })
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

	@PostMapping({ "/login" })
	public LoginResponse doLogin(@Valid @RequestBody LoginRequest req) {
		return this.loginService.doLogin(req);
	}
	
	@PostMapping("/login-validate")
	public LoginResponse validateOtpLogin(@RequestBody LoginValidateRequest req) {
		return loginService.validateOtpLogin(req);
	}

	@GetMapping({ "/getotp" })
	public LoginResponse getOtp() {
		return this.forgotPasswordService.getOtp();
	}

	@PostMapping({ "/forgotPassword" })
	public LoginResponse forgotPassword(@Valid @RequestBody ForgotPasswordRequest req) {
		return this.forgotPasswordService.forgotPassword(req);
	}

	@PostMapping({ "/valotp" })
	public LoginResponse valOtp(@RequestBody SmsMaster req) {
		return this.forgotPasswordService.valOtp(req);
	}

	@PostMapping({ "/updatePassword" })
	public LoginResponse updatePassword(@Valid @RequestBody UpdatePasswordRequest req) {
		return this.updatePasswordService.updatePassword(req);
	}

	@GetMapping({ "/bankname" })
	public BankNameResponse bankname() {
		return this.banknameDataService.bankname();
	}

	@GetMapping({ "/pincode/{code}" })
	public PincodeResponse pincode(@PathVariable("code") String pincode) {
		return this.fetchPincodedataService.pincode(pincode);
	}

	@GetMapping({ "/mcc" })
	public MccCodeResponse getAllMccData() {
		return this.banknameDataService.getAllMccData();
	}

	@GetMapping({ "/updateLogoutStatus/{username}" })
	public MccCodeResponse updateLogoutStatus(@PathVariable("username") String username) {
		MccCodeResponse resp = new MccCodeResponse();
		this.masterRepository.updateLogOutDetailsMaster("Y", new Date(), username.toUpperCase());
		resp.setStatus(true);
		resp.setMessage("success");
		return resp;
	}

	@GetMapping("/docs/customers")
	public ResponseEntity<InputStreamResource> fetchDocument(@RequestParam String id, @RequestParam String fileName)
			throws IOException {

		String basePath = "/home/ubuntu/sms-docs/customer-docs/";
		String filePath = basePath + id + "/" + fileName;

		File file = new File(filePath);

		if (!file.exists() || !file.isFile()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		String contentType = Files.probeContentType(file.toPath());
		if (contentType == null) {
			contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"");
		headers.setContentType(MediaType.parseMediaType(contentType));

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.body(new InputStreamResource(Files.newInputStream(file.toPath())));
	}
}
