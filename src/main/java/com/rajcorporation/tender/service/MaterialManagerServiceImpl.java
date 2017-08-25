package com.rajcorporation.tender.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.rajcorporation.tender.model.MaterialItem;
import com.rajcorporation.tender.repository.MaterialRepository;

@Component
public class MaterialManagerServiceImpl implements MaterialManagerService {

	@Autowired
	MaterialRepository repository;


	@Override
	@Transactional
	public MaterialItem save(MaterialItem tender) {
		return repository.save(tender);
	}

	@Override
	public MaterialItem findItem(Long id) {
		return repository.findOne(id);
	}

	@Override
	public Page<MaterialItem> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public Iterable<MaterialItem> findAll() {
		return repository.findAll();
	}
}
