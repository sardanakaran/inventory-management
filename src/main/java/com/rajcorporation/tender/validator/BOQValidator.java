package com.rajcorporation.tender.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.rajcorporation.tender.model.BOQItem;
import com.rajcorporation.tender.model.MaterialItem;

@Component
public class BOQValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return MaterialItem.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BOQItem item = (BOQItem) target;
		if (StringUtils.isEmpty(item.getTenderId())) {
			errors.rejectValue("tenderId", "tenderId.not.specified");
		}
	}
}
