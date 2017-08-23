package com.rajcorporation.tender.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rajcorporation.tender.exception.ValidationException;
import com.rajcorporation.tender.model.MaterialItem;
import com.rajcorporation.tender.model.PaginationData;
import com.rajcorporation.tender.model.Tender;
import com.rajcorporation.tender.model.TenderList;
import com.rajcorporation.tender.service.MaterialManagerService;
import com.rajcorporation.tender.service.TenderService;
import com.rajcorporation.tender.validator.MaterialValidator;
import com.rajcorporation.tender.validator.SaveGroup;
import com.rajcorporation.tender.validator.TenderValidator;
import com.rajcorporation.tender.validator.UpdateGroup;

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
}
