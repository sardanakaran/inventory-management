package com.rajcorporation.tender.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class MaterialItem extends Changeable implements Cloneable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	Long id;

	String itemDescription;
	String units;

	@JsonProperty
	public Long getId() {
		return id;
	}

	@JsonIgnore
	public void setId(Long id) {
		this.id = id;
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

	@Override
	protected MaterialItem clone() {
		MaterialItem clone = new MaterialItem();
		clone.setItemDescription(itemDescription);
		clone.setUnits(units);
		return clone;
	}

}
