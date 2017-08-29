package com.rajcorporation.tender.model;

import java.util.ArrayList;
import java.util.List;

public class BOQ extends Changeable {

	Long tenderId;
	int boqVersion;

	List<BOQItem> itemsList = new ArrayList<>();

	public Long getTenderId() {
		return tenderId;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}

	public int getBoqVersion() {
		return boqVersion;
	}

	public void setBoqVersion(int boqVersion) {
		this.boqVersion = boqVersion;
	}

	public List<BOQItem> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<BOQItem> itemsList) {
		this.itemsList = itemsList;
	}
}
