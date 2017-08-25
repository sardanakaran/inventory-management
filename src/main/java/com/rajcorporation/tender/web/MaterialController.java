package com.rajcorporation.tender.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.rajcorporation.tender.model.MaterialItem;
import com.rajcorporation.tender.service.MaterialManagerService;
import com.rajcorporation.tender.validator.MaterialValidator;
import com.rajcorporation.tender.validator.SaveGroup;

@RestController
@RequestMapping("/Material")
public class MaterialController {

	@Autowired
	MaterialValidator validator;
	
	@Autowired
	MaterialManagerService service;

	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MaterialItem> createOrUpdateItem(@RequestBody @Validated(SaveGroup.class) MaterialItem item,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException().withBindingResult(bindingResult);
		}
		return ResponseEntity.ok(service.save(item));

	}

	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MaterialItem> findItem(@RequestParam(required = false) Long id) {

		MaterialItem item = service.findItem(id);
			return ResponseEntity.ok(item);
	}
	
	@GetMapping("findPageItems")
	public ResponseEntity<Page<MaterialItem>> findPageItems(@RequestParam(required = false) Pageable page) {

		Page<MaterialItem> item = service.findAll(page);
			return ResponseEntity.ok(item);
	}
	
	@GetMapping("findAll")
	public ResponseEntity<List<MaterialItem>> findAll() {
		List<MaterialItem> itemList = new ArrayList<MaterialItem>();
		Iterable<MaterialItem> item = service.findAll();
		item.forEach(it->itemList.add(it));
			return ResponseEntity.ok(itemList);
	}
}
