package com.rajcorporation.tender.model;

import java.util.Date;

public class Agreement
{
	private Date date;	
	private double errection;
	private double supply;
	private String agreementNo;
	private double agreementAmount;
	public double getAmount() {
		return agreementAmount;
	}
	public void setAmount(double agreementAmount) {
		this.agreementAmount = agreementAmount;
	}
	public String getAgreementNo() {
		return agreementNo;
	}
	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}
	public double getSupply() {
		return supply;
	}
	public void setSupply(double supply) {
		this.supply = supply;
	}
	public double getErrection() {
		return errection;
	}
	public void setErrection(double errection) {
		this.errection = errection;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	}