package com.rajcorporation.tender.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity<BOQItem> findBoqItem(@RequestParam(required = false) Long id) {

		BOQItem item = service.findBOQItem(id);
			return ResponseEntity.ok(item);
	}
	
	@GetMapping("/getBoq")
	public ResponseEntity<BOQ> getBoq(@RequestParam(required = true) Long tenderId, @RequestParam(required = true) int boqVersion){

		List<BOQItem> item = service.findAll(tenderId, boqVersion);
		BOQ boq = new BOQ();
		boq.setTenderId(tenderId);
		boq.setBoqVersion(boqVersion);
		boq.setItemsList(item);
		return ResponseEntity.ok(boq);
	}
	
	@GetMapping("/getLatestBoq")
	public ResponseEntity<BOQ> getBoq(@RequestParam(required = true) Long tenderId) {

		List<BOQItem> items = service.find(tenderId);

		Map<Integer, List<BOQItem>> itemslistGrouped = new HashMap<Integer, List<BOQItem>>();
		int latestBoqVersion = 0;
		for (BOQItem student : items) {
			int key = student.getBoqVersion();
			if (itemslistGrouped.containsKey(key)) {
				List<BOQItem> list = itemslistGrouped.get(key);
				list.add(student);

			} else {
				List<BOQItem> list = new ArrayList<BOQItem>();
				list.add(student);
				itemslistGrouped.put(key, list);
			}
			if (key > latestBoqVersion) {
				latestBoqVersion = key;
			}
		}

		BOQ boq = new BOQ();
		boq.setTenderId(tenderId);
		boq.setBoqVersion(latestBoqVersion);
		boq.setItemsList(itemslistGrouped.get(latestBoqVersion));
		return ResponseEntity.ok(boq);
	}
}
