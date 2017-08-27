package com.rajcorporation.tender.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rajcorporation.tender.validator.SaveGroup;
import com.rajcorporation.tender.validator.UpdateGroup;

@Entity
@JsonIgnoreProperties(value = "files", allowGetters = true, allowSetters = false)
public class Tender {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotEmpty(groups = { UpdateGroup.class })
	@Column(name = "tender_id")
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}
	
	private String teanderName;
	private String description;
	private String status;
	private String districtName;
	private Date startDate;
	private Date endDate;

	@NotEmpty(groups = { SaveGroup.class, UpdateGroup.class })
	private String work;

	private String letterOfInterest;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "tender")
	@JsonIgnore
	private List<FileInfo> files = new ArrayList<>();

	@JsonProperty
	public List<FileInfo> getFiles() {
		return files;
	}

	@JsonIgnore
	public void setFiles(List<FileInfo> files) {
		this.files = files;
	}

	public Long getId() {
		return id;
	}

	public String getDistrictName() {
		return districtName;
	}

	public Tender withFile(FileInfo file) {
		this.files.add(file);
		file.setTender(this);
		return this;
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

	public String getTeanderName() {
		return teanderName;
	}

	public void setTeanderName(String teanderName) {
		this.teanderName = teanderName;
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