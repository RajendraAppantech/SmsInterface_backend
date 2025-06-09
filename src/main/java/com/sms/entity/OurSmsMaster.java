
package com.sms.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "our_sms_master")
public class OurSmsMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long smsid;

	@Column(name = "mobile_no", length = 13)
	private String mobileNo;

	@Column(name = "username", length = 100)
	private String username;

	@Column(name = "sms", length = 1024)
	private String sms;

	@Column(name = "otp", length = 32)
	private String otp;

	@Column(name = "status", length = 10)
	private String status;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "otp_date")
	private Date otpDate;

	@Column(name = "sms_response", length = 500)
	private String smsResponse;

	@Column(name = "check_status_count")
	private Integer checkStatusCount;

	@Column(name = "sms_key", length = 20)
	private String smsKey;

	@Column(name = "sms_from", length = 100)
	private String smsFrom;

	@Column(name = "template_id", length = 50)
	private String templateId;

	@Column(name = "entity_id", length = 50)
	private String entityId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "response_date")
	private Date responseDate;

	@Column(name = "send_txn_id", length = 50)
	private String sendTxnId;

	@Column(name = "response_txn_id", length = 50)
	private String responseTxnId;

	@Column(name = "bulk_file_id", length = 100)
	private String bulkFileId;

	@Column(name = "template_name", length = 100)
	private String templateName;

	@Column(name = "header_id", length = 100)
	private String headerId;

	@Column(name = "dlttelemarketer_id", length = 100)
	private String dlttelemarketerId;

	// Getters and Setters

	public Long getSmsid() {
		return smsid;
	}

	public void setSmsid(Long smsid) {
		this.smsid = smsid;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getOtpDate() {
		return otpDate;
	}

	public void setOtpDate(Date otpDate) {
		this.otpDate = otpDate;
	}

	public String getSmsResponse() {
		return smsResponse;
	}

	public void setSmsResponse(String smsResponse) {
		this.smsResponse = smsResponse;
	}

	public Integer getCheckStatusCount() {
		return checkStatusCount;
	}

	public void setCheckStatusCount(Integer checkStatusCount) {
		this.checkStatusCount = checkStatusCount;
	}

	public String getSmsKey() {
		return smsKey;
	}

	public void setSmsKey(String smsKey) {
		this.smsKey = smsKey;
	}

	public String getSmsFrom() {
		return smsFrom;
	}

	public void setSmsFrom(String smsFrom) {
		this.smsFrom = smsFrom;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public String getSendTxnId() {
		return sendTxnId;
	}

	public void setSendTxnId(String sendTxnId) {
		this.sendTxnId = sendTxnId;
	}

	public String getResponseTxnId() {
		return responseTxnId;
	}

	public void setResponseTxnId(String responseTxnId) {
		this.responseTxnId = responseTxnId;
	}

	public String getBulkFileId() {
		return bulkFileId;
	}

	public void setBulkFileId(String bulkFileId) {
		this.bulkFileId = bulkFileId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getHeaderId() {
		return headerId;
	}

	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}

	public String getDlttelemarketerId() {
		return dlttelemarketerId;
	}

	public void setDlttelemarketerId(String dlttelemarketerId) {
		this.dlttelemarketerId = dlttelemarketerId;
	}

}
