package com.rajcorporation.tender.model;

import java.util.List;

/*@Entity
@Data*/
public class BOQ {

	Long tenderId;
	int version;
	List<BOQItem> itemsList;
	
	public Long getTenderId() {
		return tenderId;
	}
	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public List<BOQItem> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<BOQItem> itemsList) {
		this.itemsList = itemsList;
	}
}
