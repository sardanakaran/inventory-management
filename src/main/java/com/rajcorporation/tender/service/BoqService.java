package com.rajcorporation.tender.service;

import java.util.List;

import com.rajcorporation.tender.model.BOQItem;

public interface BoqService {

	public BOQItem save(BOQItem tender);

	public BOQItem findBOQItem(Long id);

	public List<BOQItem> findAll(Long tenderId, int tenderVersion);
}
