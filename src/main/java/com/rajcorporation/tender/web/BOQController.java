package com.rajcorporation.tender.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rajcorporation.tender.exception.ValidationException;
import com.rajcorporation.tender.model.BOQ;
import com.rajcorporation.tender.model.BOQItem;
import com.rajcorporation.tender.model.DataInspection;
import com.rajcorporation.tender.service.BOQFinalizedException;
import com.rajcorporation.tender.service.BOQNotFoundException;
import com.rajcorporation.tender.service.BoqItemService;
import com.rajcorporation.tender.service.BoqService;
import com.rajcorporation.tender.validator.BOQCompositeValidator;

@RestController
@RequestMapping("/boq")
public class BOQController {

	@Autowired
	BOQCompositeValidator validator;

	@Autowired
	BoqService service;

	@Autowired
	BoqItemService itemService;

	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BOQ> addBOQ(@RequestBody @Validated BOQ boq, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException().withBindingResult(bindingResult);
		}
		return ResponseEntity.ok(service.save(boq));

	}

	@PutMapping(path = "/freezeBOQ", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BOQ> freezeBOQ(@RequestParam(name = "id", required = true) Long id) {
		BOQ boq = service.freeze(id);
		if (boq == null)
			return ResponseEntity.badRequest().build();

		return ResponseEntity.ok(boq);
	}

	@RequestMapping(path = "/addBoqItem", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BOQ> addItem(@RequestBody @Validated BOQItem item, @RequestParam(name = "boqId") Long boqId,
			BindingResult bindingResult) throws BOQNotFoundException, BOQFinalizedException {
		if (bindingResult.hasErrors()) {
			throw new ValidationException().withBindingResult(bindingResult);
		}

		return ResponseEntity.ok(service.addBoqItem(item, boqId));

	}

	@RequestMapping(path = "/boqItem", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BOQItem> findBoqItem(@RequestParam(required = true) Long id) {

		BOQItem item = itemService.find(id);
		return ResponseEntity.ok(item);
	}

	@RequestMapping(path = "/allBoqItems", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BOQItem>> findBoqItemsByBoqId(@RequestParam(required = true) Long boqId)
			throws BOQNotFoundException {
		BOQ boq = service.find(boqId);

		if (boq == null)
			throw new BOQNotFoundException("BOQ not found!");

		List<BOQItem> items = itemService.findByBOQ(boq);
		return ResponseEntity.ok(items);
	}

	@GetMapping("/allBoqz")
	public ResponseEntity<List<BOQ>> getBoq(@RequestParam(required = true) Long tenderId) {
		List<BOQ> boqZ = service.findByTenderId(tenderId);
		return ResponseEntity.ok(boqZ);

	}

	@PostMapping("/cloneBOQ")
	public ResponseEntity<BOQ> cloneBOQ(@RequestParam(required = true) Long boqId) throws BOQNotFoundException {
		BOQ boq = service.cloneBOQ(boqId);
		return ResponseEntity.ok(boq);
	}

	@GetMapping("/latestBOQ")
	public ResponseEntity<BOQ> getLatestBoq(@RequestParam(required = true) Long tenderId) {
		List<BOQ> boqZ = service.findByTenderId(tenderId);
		Optional<BOQ> max = boqZ.stream().max((boq1, boq2) -> {
			return boq1.getModifiedDateTime().compareTo(boq2.getModifiedDateTime());
		});

		return ResponseEntity.ok(max.get());
	}

	@PostMapping("/addDataInspection")
	public ResponseEntity<BOQItem> addDataInspection(@RequestParam("itemId") Long boqItemId,
			@RequestBody DataInspection inspection) {
		BOQItem item = itemService.addDataInspection(boqItemId, inspection);
		return ResponseEntity.ok(item);
	}

	@GetMapping("/getDataInspection")
	public ResponseEntity<List<DataInspection>> getDataInspection(@RequestParam Long boqItemId) {
		List<DataInspection> dataInspectionItems = itemService.getDataInspection(boqItemId);
		return ResponseEntity.ok(dataInspectionItems);
	}

}
