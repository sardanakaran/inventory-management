package com.rajcorporation.tender.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.rajcorporation.tender.model.MaterialItem;


public interface MaterialManagerService {

	public MaterialItem save(MaterialItem tender);

	public MaterialItem findItem(Long id);

	public Page<MaterialItem> findAll(Pageable pageable);

	public Iterable<MaterialItem> findAll();

}
