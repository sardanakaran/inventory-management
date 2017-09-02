package com.rajcorporation.tender.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

import com.rajcorporation.tender.validator.SaveGroup;
import com.rajcorporation.tender.validator.UpdateGroup;

@Entity
public class Tender extends Changeable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotEmpty(groups = { UpdateGroup.class })
	@Column(name = "tender_id")
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	private String tenderName;
	private String description;
	private String status;
	private String districtName;
	private Date startDate;
	private Date endDate;

	@NotEmpty(groups = { SaveGroup.class, UpdateGroup.class })
	private String work;

	private String letterOfInterest;

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

	public String getTenderName() {
		return tenderName;
	}

	public void setTenderName(String tenderName) {
		this.tenderName = tenderName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}