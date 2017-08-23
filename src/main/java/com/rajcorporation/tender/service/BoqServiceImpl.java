package com.rajcorporation.tender.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rajcorporation.tender.model.BOQItem;
import com.rajcorporation.tender.repository.BOQItemRepository;

@Component
public class BoqServiceImpl implements BoqService {

	@Autowired
	BOQItemRepository repository;


	@Override
	@Transactional
	public BOQItem save(BOQItem tender) {
		return repository.save(tender);
	}

	public void saveAll(List<BOQItem> items) {
		items.forEach(item->save(item));
	}
	
	@Override
	public BOQItem findBOQItem(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<BOQItem> findAll(Long tenderId, int boqVersion) {
		return repository.GetAllBOQItems(tenderId, boqVersion);
	}

}
