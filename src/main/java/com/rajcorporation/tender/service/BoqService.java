package com.rajcorporation.tender.service;

import java.util.List;

import com.rajcorporation.tender.model.BOQ;
import com.rajcorporation.tender.model.BOQItem;

public interface BoqService {

	public BOQ save(BOQ boq);

	public BOQ find(Long id);

	List<BOQ> findByTenderId(Long tendId);

	BOQ findLatest(Long tenderId);

	public BOQ freeze(Long id);

	public BOQ addBoqItem(BOQItem item, Long boqId) throws BOQNotFoundException, BOQFinalizedException;

	public BOQ cloneBOQ(Long boqId) throws BOQNotFoundException;

}
