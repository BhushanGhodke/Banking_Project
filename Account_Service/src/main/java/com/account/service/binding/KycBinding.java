package com.account.service.binding;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


public class KycBinding {
	
	    private Integer kycId;
	    private String aadhaarNumber;
	    private String panNumber;
	    private String passportNumber;
	    private LocalDate dob;
	    private String status; // VERIFIED, PENDING
	    
	    private Integer userId;
	   
	    private Integer accountId;
	    
		public Integer getKycId() {
			return kycId;
		}
		public void setKycId(Integer kycId) {
			this.kycId = kycId;
		}
		public String getAadhaarNumber() {
			return aadhaarNumber;
		}
		public void setAadhaarNumber(String aadhaarNumber) {
			this.aadhaarNumber = aadhaarNumber;
		}
		public String getPanNumber() {
			return panNumber;
		}
		public void setPanNumber(String panNumber) {
			this.panNumber = panNumber;
		}
		public String getPassportNumber() {
			return passportNumber;
		}
		public void setPassportNumber(String passportNumber) {
			this.passportNumber = passportNumber;
		}
		public LocalDate getDob() {
			return dob;
		}
		public void setDob(LocalDate dob) {
			this.dob = dob;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		
		public Integer getAccountId() {
			return accountId;
		}
		public void setAccountId(Integer accountId) {
			this.accountId = accountId;
		}
	    
	    
}
