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
public class BOQ extends Changeable implements Cloneable {

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	Long tenderId;

	@OneToMany(mappedBy = "boq", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

	public List<BOQItem> getBoqItems() {
		return boqItems;
	}

	public void setBoqItems(List<BOQItem> boqItems) {
		for (BOQItem boqItem : boqItems) {
			boqItem.withBOQ(this);
		}
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

	@Override
	public BOQ clone() {
		BOQ clone = new BOQ();
		clone.setTenderId(tenderId);
		clone.setBoqItems(cloneBOQItems(clone));
		return clone;
	}

	private List<BOQItem> cloneBOQItems(BOQ boqClone) {
		final List<BOQItem> clone = new ArrayList<>();
		if (this.boqItems != null && !this.boqItems.isEmpty()) {
			boqItems.forEach(item -> {
				BOQItem itemClone = item.clone();
				itemClone.withBOQ(boqClone);
				clone.add(itemClone);
			});
		}
		return clone;
	}

}
