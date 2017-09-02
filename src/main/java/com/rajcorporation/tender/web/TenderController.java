/**
 * 
 */
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

		Tender updated = compareAndSet(actualTender, tender);

		return ResponseEntity.ok(service.save(updated));

	}

	private Tender compareAndSet(Tender actualTender, Tender tender) {
		actualTender.setDescription(tender.getDescription());
		actualTender.setEndDate(tender.getEndDate());
		actualTender.setStartDate(tender.getStartDate());
		actualTender.setStatus(tender.getStatus());
		actualTender.setLetterOfInterest(tender.getLetterOfInterest());
		actualTender.setTenderName(tender.getTenderName());
		actualTender.setDistrictName(tender.getDistrictName());
		actualTender.setWork(tender.getWork());

		return actualTender;
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

	@PostMapping(value = "/upload")
	public boolean attachFile(@RequestParam(required = true) Long tenderId, @RequestParam MultipartFile file) {
		return service.attachFile(tenderId, file);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.DELETE)
	public boolean removeFile(@RequestParam(required = true) Long fileId,
			@RequestParam(required = true) Long tenderId) {
		return service.removeFile(tenderId, fileId);
	}

	@GetMapping("/files/{fileId:.+}")
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
		Resource file = service.getFile(fileId);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE).body(file);
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
