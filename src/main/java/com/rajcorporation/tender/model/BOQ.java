package com.rajcorporation.tender.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BOQ {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	Long tenderId;
	int boqVersion;

//	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "boqitem")
//	List<BOQItem> itemsList = new ArrayList<>();

//	public BOQ withItem(BOQItem item) {
//		if (itemsList.add(item))
//			item.setBoq(this);
//		return this;
//	}
//
//	public BOQ withItems(List<BOQItem> items) {
//		items.forEach(item -> withItem(item));
//		return this;
//	}
//
//	public BOQ removeItem(BOQItem item) {
//		if (itemsList.remove(item)) {
//			item.setBoq(null);
//		}
//		return this;
//	}

}
