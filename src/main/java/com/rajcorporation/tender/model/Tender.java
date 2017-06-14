package com.rajcorporation.tender.model;
public class Tender
{
	private String tenderNo;
	private String districtName;
	private String work;
	private String letterOfInterest;
	private Agreement agreement;
	public String getTenderNo() {
		return tenderNo;
	}
	public void setTenderNo(String tenderNo) {
		this.tenderNo = tenderNo;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getLetterOfInterest() {
		return letterOfInterest;
	}
	public void setLetterOfInterest(String letterOfInterest) {
		this.letterOfInterest = letterOfInterest;
	}
	public Agreement getAgreement() {
		return agreement;
	}
	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}
	
	}