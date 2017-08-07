package com.rajcorporation.tender.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rajcorporation.tender.model.BOQItem;
import com.rajcorporation.tender.repository.BOQRepository;

@Component
public class BoqServiceImpl implements BoqService {

	@Autowired
	BOQRepository repository;


	@Override
	@Transactional
	public BOQItem save(BOQItem tender) {
		return repository.save(tender);
	}

	@Override
	public BOQItem findBOQItem(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<BOQItem> findAll(Long tenderId, int tenderVersion) {
		//need to implement search based on tender ID and version No
		return (List<BOQItem>) repository.findAll();
	}

}
