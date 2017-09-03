/**
 * 
 */
package com.rajcorporation.tender.advice;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rajcorporation.tender.exception.StorageFileNotFoundException;
import com.rajcorporation.tender.exception.ValidationException;
import com.rajcorporation.tender.model.ErrorInfo;

/**
 * @author karan
 *
 */
@RestControllerAdvice
public class CommonExceptionHandler {

	@Autowired
	MessageSource messageSource;

	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<ErrorInfo> handleValidationException(ValidationException ex) {
		return getErrorResponse(ex.getBindingResult());
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorInfo> handleException(Exception ex) {
		Throwable throwable = extractTheRealCause(ex);

		return ResponseEntity.badRequest().body(new ErrorInfo().withError(throwable.getMessage()));
	}

	private Throwable extractTheRealCause(Exception ex) {
		Throwable throwable = ex;
		Throwable toReturn = ex;
		while ((throwable = throwable.getCause()) != null)
			toReturn = throwable;

		return toReturn;
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<ErrorInfo> getErrorResponse(BindingResult result) {
		ErrorInfo info = new ErrorInfo();
		for (ObjectError objectError : result.getAllErrors()) {
			String message;
			try {
				message = messageSource.getMessage(objectError.getCode(), objectError.getArguments(), Locale.ENGLISH);
				info.withError(message);
			} catch (NoSuchMessageException e) {
				message = null;
			}
		}
		return ResponseEntity.badRequest().body(info);
	}

}
