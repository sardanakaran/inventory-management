package com.rajcorporation.tender.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rajcorporation.tender.validator.SaveGroup;
import com.rajcorporation.tender.validator.UpdateGroup;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(value = "files", allowGetters = true, allowSetters = false)
public class Tender {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotEmpty(groups = { UpdateGroup.class })
	@Column(name = "tender_id")
	private Long id;

	private String districtName;

	@NotEmpty(groups = { SaveGroup.class, UpdateGroup.class })
	private String work;

	private String letterOfInterest;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "tender")
	@JsonIgnore
	private List<FileInfo> files = new ArrayList<>();

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "boq_id")
	private BOQ boq;

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

}