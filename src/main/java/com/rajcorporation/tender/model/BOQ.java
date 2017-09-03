package com.rajcorporation.tender.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class BOQ extends Changeable {

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	Long tenderId;

	@OneToMany(mappedBy = "boq", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	List<BOQItem> boqItems = new ArrayList<BOQItem>();

	Status status = Status.OPEN;

	public Long getTenderId() {
		return tenderId;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@JsonProperty
	public Long getId() {
		return id;
	}

	@JsonIgnore
	public List<BOQItem> getBoqItems() {
		return boqItems;
	}

	@JsonIgnore
	public void setBoqItems(List<BOQItem> boqItems) {
		this.boqItems = boqItems;
	}

	@JsonIgnore
	public void setId(Long id) {
		this.id = id;
	}

	public BOQ withItem(BOQItem item) {
		item.withBOQ(this);
		boqItems.add(item);
		return this;
	}

}
