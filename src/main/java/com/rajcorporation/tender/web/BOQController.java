package com.rajcorporation.tender.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajcorporation.tender.model.BOQItem;
import com.rajcorporation.tender.model.DataInspection;
import com.rajcorporation.tender.model.Tender;
import com.rajcorporation.tender.service.TenderService;

@RestController
@RequestMapping("/Boq")
public class BOQController {

	@Autowired
	TenderService service;

	@PostMapping(value = "/{tenderId}/addBOQ")
	public ResponseEntity<Tender> addBOQItems(@PathVariable Long tenderId, @RequestBody List<BOQItem> boqItems) {
		Tender tender = service.addBOQItems(tenderId, boqItems);
		return ResponseEntity.ok(tender);
	}

	@PostMapping(value = "/{tenderId}/{boqId}/addDataInspection")
	public ResponseEntity<Tender> addDataInspection(@PathVariable Long tenderId, @PathVariable Long boqId,
			@RequestBody DataInspection dataInspection) {
		Tender tender = service.addDataInspection(tenderId, boqId, dataInspection);
		return ResponseEntity.ok(tender);
	}

	@PostMapping(value = "/{tenderId}/{boqId}/{dataInspectionId}/{fileId}/linkFile")
	public ResponseEntity<Tender> linkFile(@PathVariable Long tenderId, @PathVariable Long boqId,
			@PathVariable Long dataInspectionId, @PathVariable Long fileId) {
		Tender tender = service.linkFile(tenderId, boqId, dataInspectionId, fileId);
		return ResponseEntity.ok(tender);

	}

}
