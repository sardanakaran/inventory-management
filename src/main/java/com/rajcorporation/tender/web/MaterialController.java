package com.rajcorporation.tender.web;

import java.util.ArrayList;
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
import com.rajcorporation.tender.model.MaterialItem;
import com.rajcorporation.tender.model.MaterialList;
import com.rajcorporation.tender.service.MaterialManagerService;
import com.rajcorporation.tender.validator.MaterialValidator;
import com.rajcorporation.tender.validator.SaveGroup;

@RestController
@RequestMapping("/material")
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
	public ResponseEntity<MaterialList> findItem(@RequestParam(required = false) Long id) {
		MaterialList materialList = new MaterialList();
		List<MaterialItem> materialItemList = new ArrayList<MaterialItem>();
		materialList.setMaterials(materialItemList);

		if (id != null) {
			materialItemList.add(service.findItem(id));
		} else {
			Iterable<MaterialItem> item = service.findAll();
			item.forEach(it -> materialItemList.add(it));
		}
		return ResponseEntity.ok(materialList);
	}
}
