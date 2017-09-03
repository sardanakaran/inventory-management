package com.rajcorporation.tender.validator;

import java.util.List;
import java.util.Optional;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.rajcorporation.tender.model.BOQ;
import com.rajcorporation.tender.model.Status;
import com.rajcorporation.tender.model.Tender;
import com.rajcorporation.tender.model.TenderStatus;
import com.rajcorporation.tender.repository.BOQRepository;
import com.rajcorporation.tender.repository.TenderRepository;

public class BOQValidator implements Validator {

	TenderRepository tenderRepository;

	BOQRepository boqRepository;

	public BOQValidator(TenderRepository tenderRepository, BOQRepository boqRepository) {
		super();
		this.tenderRepository = tenderRepository;
		this.boqRepository = boqRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return BOQ.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BOQ item = (BOQ) target;
		BOQ existingBOQ = null;
		Tender tender = null;
		if (StringUtils.isEmpty(item.getTenderId())) {
			errors.rejectValue("tenderId", "tender.not.specified");
		} else if ((tender = getTender(item.getTenderId())) == null) {
			errors.rejectValue("tenderId", "tender.non.existent");
		} else if (tender.getStatus() != TenderStatus.OPEN) {
			errors.rejectValue("tenderId", "tender.not.open");
		} else if ((existingBOQ = getBOQ(item.getTenderId())) != null && existingBOQ.getStatus() == Status.OPEN) {
			errors.rejectValue("tenderId", "boq.still.open");
		}

	}

	private Tender getTender(Long tenderId) {
		return tenderRepository.findOne(tenderId);
	}

	private BOQ getBOQ(Long tenderId) {
		List<BOQ> boqZ = boqRepository.findByTenderId(tenderId);
		Optional<BOQ> max = boqZ.stream().max((boq1, boq2) -> {
			return boq1.getModifiedDateTime().compareTo(boq2.getModifiedDateTime());
		});

		return max.isPresent() ? max.get() : null;

	}
}
