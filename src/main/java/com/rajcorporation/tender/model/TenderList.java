/**
 * 
 */
package com.rajcorporation.tender.model;

import java.util.List;

/**
 * @author karan
 *
 */
public class TenderList {
	PaginationData paginationData;
	List<Tender> tenders;

	public PaginationData getPaginationData() {
		return paginationData;
	}

	public void setPaginationData(PaginationData paginationData) {
		this.paginationData = paginationData;
	}

	public List<Tender> getTenders() {
		return tenders;
	}

	public void setTenders(List<Tender> tenders) {
		this.tenders = tenders;
	}

}
