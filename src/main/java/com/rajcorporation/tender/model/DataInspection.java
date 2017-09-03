/**
 * 
 */
package com.rajcorporation.tender.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Karan
 *
 */

@Entity
public class DataInspection extends Changeable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "boqItem_id")
	@JsonIgnore
	BOQItem boqItem;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "inspection_id")
	List<FileInfo> files;

	public DataInspection withFile(FileInfo info) {
		if (files == null)
			files = new ArrayList<>();
		files.add(info);
		return this;
	}

	@JsonProperty
	public Long getId() {
		return id;
	}

	@JsonIgnore
	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public BOQItem getBoqItem() {
		return boqItem;
	}

	@JsonIgnore
	public void setBoqItem(BOQItem boqItem) {
		this.boqItem = boqItem;
	}

	public List<FileInfo> getFiles() {
		return files;
	}

	public void setFiles(List<FileInfo> files) {
		this.files = files;
	}

}
