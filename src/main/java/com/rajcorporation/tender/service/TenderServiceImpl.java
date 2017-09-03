/**
 * 
 */
package com.rajcorporation.tender.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.rajcorporation.tender.exception.TenderDoesNotExistException;
import com.rajcorporation.tender.model.DataInspection;
import com.rajcorporation.tender.model.FileInfo;
import com.rajcorporation.tender.model.Tender;
import com.rajcorporation.tender.repository.DataInspectionRepository;
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
	FileInfoRepository fileRepository;

	@Autowired
	DataInspectionRepository dataInspectionRepository;

	@Autowired
	StorageService storage;

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
		info.setTenderId(tenderId);

		fileRepository.save(info);

		return true;
	}

	@Override
	@Transactional
	public boolean removeFile(Long tenderId, Long fileId) {
		FileInfo fileInfo = fileRepository.findOne(fileId);

		boolean removed = storage.remove(fileInfo.getPath());

		if (removed)
			fileRepository.delete(fileInfo);

		return fileRepository.findOne(fileId) == null;
	}

	@Override
	public Resource getFile(Long fileId) {
		FileInfo fileInfo = fileRepository.findOne(fileId);
		return storage.loadAsResource(fileInfo.getPath());
	}

	@Override
	@Transactional
	public Tender linkFile(Long tenderId, Long boqId, Long dataInspectionId, Long fileId) {
		Tender tender = findTender(tenderId);
		if (Objects.isNull(tender))
			throw new RuntimeException("Tender not found");

		DataInspection dataInspection = dataInspectionRepository.findOne(dataInspectionId);
		if (Objects.isNull(dataInspection))
			throw new RuntimeException("Data Inspection instance not found");

		FileInfo fileInfo = fileRepository.findOne(fileId);
		if (Objects.isNull(dataInspection))
			throw new RuntimeException("File to link not found");

		dataInspection.withFile(fileInfo);

		return tender;
	}

	@Override
	public List<FileInfo> getFiles(Long tenderId) {
		return fileRepository.findByTenderId(tenderId);
	}

}
