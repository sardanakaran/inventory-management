/**
 * 
 */
package com.rajcorporation.tender.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rajcorporation.tender.model.Tender;

/**
 * @author karan
 *
 */
public interface TenderRepository extends PagingAndSortingRepository<Tender, Long>{

}
