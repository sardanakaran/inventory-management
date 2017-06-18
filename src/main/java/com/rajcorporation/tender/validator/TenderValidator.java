/**
 * 
 */
package com.rajcorporation.tender.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.rajcorporation.tender.model.Tender;

/**
 * @author karan
 *
 */
@Component
public class TenderValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Tender.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Tender tender = (Tender) target;
		if (StringUtils.isEmpty(tender.getWork())) {
			errors.rejectValue("work", "work.not.specified");
		}
	}

}
