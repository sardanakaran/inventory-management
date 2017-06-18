/**
 * 
 */
package com.rajcorporation.tender.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author karan
 *
 */
public class ErrorInfo {

	List<String> errors;

	public ErrorInfo() {
		this.errors = new ArrayList<>();
	}

	public ErrorInfo withError(String error) {
		this.errors.add(error);
		return this;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
