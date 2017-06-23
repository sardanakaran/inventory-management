/**
 * 
 */
package com.rajcorporation.tender.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.rajcorporation.tender.exception.TenderDoesNotExistException;
import com.rajcorporation.tender.model.FileInfo;
import com.rajcorporation.tender.model.Tender;
import com.rajcorporation.tender.repository.FileInfoRepository;
import com.rajcorporation.tender.repository.TenderRepository;

/**
 * @author karan
 *
 */
@Component
public class TenderServiceImpl implements TenderService {

	@Autowired
	TenderRepository repository;

	@Autowired
	StorageService storage;

	@Autowired
	FileInfoRepository fileRepo;

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
	public boolean attachFile(Long tenderId, MultipartFile file) {
		Tender tender = repository.findOne(tenderId);

		if (tender == null)
			throw new TenderDoesNotExistException(String.format("No tender exists for %d", tenderId));

		String path = storage.store(file, String.valueOf(tenderId));

		FileInfo info = new FileInfo();
		info.setName(file.getOriginalFilename());
		info.setPath(path);
		info.setUploadedAt(new Date());
		info.setUploadedBy("dummy");

		tender.withFile(info);

		return true;
	}

	@Override
	@Transactional
	public boolean removeFile(Long tenderId, Long fileId) {
		Tender tender = repository.findOne(tenderId);

		FileInfo fileInfo = fileRepo.findOne(fileId);

		boolean removed = storage.remove(fileInfo.getPath());

		if (removed)
			tender.getFiles().removeIf(file -> file.getId() == fileId);

		return !tender.getFiles().stream().anyMatch(file -> file.getId() == fileId);
	}

	@Override
	public Resource getFile(Long fileId) {
		FileInfo fileInfo = fileRepo.findOne(fileId);
		return storage.loadAsResource(fileInfo.getPath());
	}

}
