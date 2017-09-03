/**
 * 
 */
package com.rajcorporation.tender.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rajcorporation.tender.model.BOQ;
import com.rajcorporation.tender.model.BOQItem;
import com.rajcorporation.tender.model.DataInspection;
import com.rajcorporation.tender.repository.BOQItemRepository;
import com.rajcorporation.tender.repository.DataInspectionRepository;

/**
 * @author Karan
 *
 */
@Component
public class BoqItemServiceImpl implements BoqItemService {

	@Autowired
	BOQItemRepository itemRepo;

	@Autowired
	DataInspectionRepository dataInspectionRepo;

	@Override
	public BOQItem find(Long id) {
		return itemRepo.findOne(id);
	}

	@Override
	public List<BOQItem> findByBOQ(BOQ boq) {
		return itemRepo.findByBoq(boq);
	}

	@Override
	@Transactional
	public BOQItem save(BOQItem item) {
		return itemRepo.save(item);
	}

	@Override
	@Transactional
	public BOQItem addDataInspection(Long boqItemId, DataInspection inspection) {
		BOQItem boqItem = itemRepo.findOne(boqItemId);
		boqItem.withDataInspection(inspection);

		return boqItem;
	}

	@Override
	public List<DataInspection> getDataInspection(Long boqItemId) {
		BOQItem boqItem = itemRepo.findOne(boqItemId);
		return dataInspectionRepo.findByBoqItem(boqItem);
	}

}
