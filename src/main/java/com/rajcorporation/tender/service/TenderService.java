/**
 * 
 */
package com.rajcorporation.tender.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rajcorporation.tender.model.Tender;

/**
 * @author karan
 *
 */
public interface TenderService {

	public Tender save(Tender tender);

	public Tender findTender(Long id);

	public Page<Tender> findAll(Pageable pageable);

	public boolean attachFile(Long tenderId, String fileName);

	public boolean removeFile(Long tendedId, Long fileId);

}
