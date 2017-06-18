package com.rajcorporation.tender.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotEmpty;

import com.rajcorporation.tender.web.SaveGroup;
import com.rajcorporation.tender.web.UpdateGroup;

@Entity
public class Tender {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotEmpty(groups = { UpdateGroup.class })
	private Long id;

	private String districtName;
	
	@NotEmpty(groups = { SaveGroup.class, UpdateGroup.class })
	private String work;
	
	private String letterOfInterest;

	@OneToOne(targetEntity = Agreement.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "agreement_id")
	private Agreement agreement;

	public Long getId() {
		return id;
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