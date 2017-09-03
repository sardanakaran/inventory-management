package com.rajcorporation.tender.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.rajcorporation.tender.model.BOQ;
import com.rajcorporation.tender.model.BOQItem;
import com.rajcorporation.tender.model.DataInspection;
import com.rajcorporation.tender.model.Tender;
import com.rajcorporation.tender.repository.BOQRepository;
import com.rajcorporation.tender.repository.TenderRepository;

public class BOQItemValidator implements Validator {

	BOQRepository boqRepository;

	TenderRepository tenderRepo;

	public BOQItemValidator(BOQRepository boqRepository, TenderRepository tenderRepo) {
		this.boqRepository = boqRepository;
		this.tenderRepo = tenderRepo;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return BOQItem.class.isAssignableFrom(clazz) || DataInspection.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BOQItem item = (BOQItem) target;
		BOQ boq = null;
		Tender tender = null;

	}

	private BOQ getBOQ(Long boqId) {
		return boqRepository.findOne(boqId);
	}
}
