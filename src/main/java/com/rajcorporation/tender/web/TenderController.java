/**
 * 
 */
package com.rajcorporation.tender.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.rajcorporation.tender.model.PaginationData;
import com.rajcorporation.tender.model.Tender;
import com.rajcorporation.tender.model.TenderList;
import com.rajcorporation.tender.service.TenderService;
import com.rajcorporation.tender.validator.SaveGroup;
import com.rajcorporation.tender.validator.TenderValidator;
import com.rajcorporation.tender.validator.UpdateGroup;

/**
 * @author karan
 *
 */
@RestController
@RequestMapping("/tender")
public class TenderController {

	@Autowired
	TenderValidator validator;

	@Autowired
	TenderService service;

	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Tender> createTender(@RequestBody @Validated(SaveGroup.class) Tender tender,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException().withBindingResult(bindingResult);
		}
		return ResponseEntity.ok(service.save(tender));

	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Tender> updateTender(@RequestBody @Validated(UpdateGroup.class) Tender tender,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException().withBindingResult(bindingResult);
		}
		Tender actualTender = service.findTender(tender.getId());
		if (actualTender == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(service.save(tender));

	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TenderList> findTender(@RequestParam(required = false) Long id, @RequestParam int page,
			@RequestParam int size) {

		TenderList list = new TenderList();
		if (id == null) {
			PageRequest req = new PageRequest(page, size);
			Page<Tender> pagedInformation = service.findAll(req);

			list.setTenders(pagedInformation.getContent());
			list.setPaginationData(getPaginationData(pagedInformation));

			return ResponseEntity.ok(list);
		} else {
			Tender tender = service.findTender(id);
			if (tender != null)
				list.setTenders(Arrays.asList(tender));
			return ResponseEntity.ok(list);
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public boolean attachFile(@RequestParam(required = true) String fileName,
			@RequestParam(required = true) Long tenderId) {
		return service.attachFile(tenderId, fileName);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.DELETE)
	public boolean removeFile(@RequestParam(required = true) Long fileId,
			@RequestParam(required = true) Long tenderId) {
		return service.removeFile(tenderId, fileId);
	}

	private <T> PaginationData getPaginationData(Page<T> pagedInformation) {
		long totalElements = pagedInformation.getTotalElements();
		PaginationData data = new PaginationData();
		data.setTotalElements(totalElements);
		data.setElements(pagedInformation.getNumberOfElements());
		data.setSize(pagedInformation.getSize());
		return data;
	}

}
