package com.rajcorporation.tender.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class MaterialItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long	id;
	String	itemDescription;
	String	units;
	
	public Long getId()
	{
		return id;
	}
	
	public String getItemDescription()
	{
		return itemDescription;
	}
	
	public void setItemDescription(String itemDescription)
	{
		this.itemDescription= itemDescription;
	}
	
	public String getUnits()
	{
		return units;
	}
	
	public void setUnits(String units)
	{
		this.units= units;
	}
}
