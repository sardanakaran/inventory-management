package com.rajcorporation.tender.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rajcorporation.tender.model.BOQ;
import com.rajcorporation.tender.model.BOQItem;
import com.rajcorporation.tender.model.Status;
import com.rajcorporation.tender.repository.BOQRepository;

@Component
public class BoqServiceImpl implements BoqService {

	@Autowired
	BOQRepository boqRepository;

	@Override
	@Transactional
	public BOQ save(BOQ boq) {
		return boqRepository.save(boq);
	}

	@Override
	public BOQ find(Long id) {
		return boqRepository.findOne(id);
	}

	@Override
	public List<BOQ> findByTenderId(Long tenderId) {
		return boqRepository.findByTenderId(tenderId);
	}

	@Override
	@Transactional
	public BOQ freeze(Long id) {
		BOQ boq = null;
		if ((boq = boqRepository.findOne(id)) != null)
			boq.setStatus(Status.FINALIZED);
		return boq;
	}

	@Override
	@Transactional
	public BOQ addBoqItem(BOQItem item, Long boqId) throws BOQNotFoundException, BOQFinalizedException {
		BOQ boq = null;
		if ((boq = boqRepository.findOne(boqId)) != null) {
			if (boq.getStatus() == Status.OPEN)
				return boq.withItem(item);
			else
				throw new BOQFinalizedException("Already final; please create a new one");
		} else
			throw new BOQNotFoundException("BOQ not found!");
	}

}
