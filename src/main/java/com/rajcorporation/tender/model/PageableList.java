/**
 * 
 */
package com.rajcorporation.tender.model;

import java.util.List;

/**
 * @author Karan
 *
 */
public class PageableList<T> {

	PaginationData paginationData;
	List<T> data;

	public PaginationData getPaginationData() {
		return paginationData;
	}

	public void setPaginationData(PaginationData paginationData) {
		this.paginationData = paginationData;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
