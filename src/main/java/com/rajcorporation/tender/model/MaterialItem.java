package com.rajcorporation.tender.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MaterialItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String itemDescription;
	String units;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "boqItem_id")
	@JsonIgnore
	BOQItem boqItem;

	public Long getId() {
		return id;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public BOQItem getBoqItem() {
		return boqItem;
	}

	public void setBoqItem(BOQItem boqItem) {
		this.boqItem = boqItem;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
