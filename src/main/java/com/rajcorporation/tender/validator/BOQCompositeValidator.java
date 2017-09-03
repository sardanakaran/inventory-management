/**
 * 
 */
package com.rajcorporation.tender.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Karan
 *
 */
public class BOQCompositeValidator implements Validator {

	private final Validator[] validators;

	public BOQCompositeValidator(Validator[] validators) {
		this.validators = validators;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		for (Validator v : validators) {
			if (v.supports(clazz)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void validate(Object obj, Errors errors) {
		for (Validator v : validators) {
			if (v.supports(obj.getClass())) {
				v.validate(obj, errors);
			}
		}
	}

}
