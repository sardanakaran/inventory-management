package com.rajcorporation.tender.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rajcorporation.tender.exception.ValidationException;
import com.rajcorporation.tender.model.BOQ;
import com.rajcorporation.tender.model.BOQItem;
import com.rajcorporation.tender.service.BoqService;
import com.rajcorporation.tender.validator.BOQValidator;
import com.rajcorporation.tender.validator.SaveGroup;

@RestController
@RequestMapping("/Boq")
public class BOQController {

	@Autowired
	BOQValidator validator;
	
	@Autowired
	BoqService service;

	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BOQItem> createOrUpdateItem(@RequestBody @Validated(SaveGroup.class) BOQItem item,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException().withBindingResult(bindingResult);
		}
		return ResponseEntity.ok(service.save(item));

	}

	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BOQItem> findItem(@RequestParam(required = false) Long id) {

		BOQItem item = service.findBOQItem(id);
			return ResponseEntity.ok(item);
	}
	
	/*@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BOQ> findAll(@RequestParam(required = true) Long tenderId, @RequestParam(required = true) int tenderVersion){

		List<BOQItem> item = service.findAll(tenderId, tenderVersion);
		BOQ boq = new BOQ();
		boq.setTenderId(tenderId);
		boq.setVersion(tenderVersion);
		boq.setItemsList(item);
		return ResponseEntity.ok(boq);
	}*/
}
