/**
 * 
 */
package com.rajcorporation.tender.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rajcorporation.tender.model.BOQ;

/**
 * @author Karan
 *
 */
public interface BOQRepository extends PagingAndSortingRepository<BOQ, Long> {
	List<BOQ> findByTenderId(Long tenderId);
}
