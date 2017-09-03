/**
 * 
 */
package com.rajcorporation.tender.service;

import java.util.List;

import com.rajcorporation.tender.model.BOQ;
import com.rajcorporation.tender.model.BOQItem;
import com.rajcorporation.tender.model.DataInspection;

/**
 * @author Karan
 *
 */
public interface BoqItemService {
	public BOQItem find(Long id);

	public List<BOQItem> findByBOQ(BOQ boq);

	public BOQItem save(BOQItem item);

	public BOQItem addDataInspection(Long boqItemId, DataInspection inspection);

	public List<DataInspection> getDataInspection(Long boqItemId);

}
