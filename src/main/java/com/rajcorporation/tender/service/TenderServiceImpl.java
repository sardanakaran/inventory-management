/**
 * 
 */
package com.rajcorporation.tender.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.rajcorporation.tender.model.FileInfo;
import com.rajcorporation.tender.model.Tender;
import com.rajcorporation.tender.repository.TenderRepository;

/**
 * @author karan
 *
 */
@Component
public class TenderServiceImpl implements TenderService {

	@Autowired
	TenderRepository repository;

	@Value("${file.upload.directory}")
	String location;

	@Override
	@Transactional
	public Tender save(Tender tender) {
		return repository.save(tender);
	}

	@Override
	public Tender findTender(Long id) {
		return repository.findOne(id);
	}

	@Override
	public Page<Tender> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	@Transactional
	public boolean attachFile(Long tenderId, String fileName) {
		Tender tender = repository.findOne(tenderId);
		FileInfo info = FileUtils.uploadFile(fileName, location);
		tender.withFile(info);

		return true;
	}

	@Override
	@Transactional
	public boolean removeFile(Long tenderId, Long fileId) {
		Tender tender = repository.findOne(tenderId);
		boolean success = FileUtils.removeFile(fileId, location);
		if (success)
			tender.getFiles().removeIf(file -> file.getId() == fileId);

		return !tender.getFiles().stream().anyMatch(file -> file.getId() == fileId);
	}

}