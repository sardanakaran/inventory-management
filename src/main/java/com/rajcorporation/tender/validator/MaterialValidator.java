package com.rajcorporation.tender.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.rajcorporation.tender.model.MaterialItem;

@Component
public class MaterialValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return MaterialItem.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MaterialItem item = (MaterialItem) target;
		if (StringUtils.isEmpty(item.getItemDescription())) {
			errors.rejectValue("itemDescription", "itemDescription.not.specified");
			if (StringUtils.isEmpty(item.getUnits())) {
				errors.rejectValue("unit", "unit.not.specified");
			}
		}
	}
}
