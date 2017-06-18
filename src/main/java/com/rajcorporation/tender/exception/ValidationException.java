/**
 * 
 */
package com.rajcorporation.tender.exception;

import org.springframework.validation.BindingResult;

/**
 * @author karan
 *
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	BindingResult bindingResult;

	public BindingResult getBindingResult() {
		return bindingResult;
	}

	public ValidationException withBindingResult(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
