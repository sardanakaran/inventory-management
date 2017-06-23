/**
 * 
 */
package com.rajcorporation.tender.exception;

/**
 * @author karan
 *
 */
public class TenderDoesNotExistException extends RuntimeException {
	private static final long serialVersionUID = 2054720212045375451L;

	public TenderDoesNotExistException(String msg) {
		super(msg);
	}

}
