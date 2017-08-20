package com.rajcorporation.tender.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rajcorporation.tender.model.BOQItem;

public interface BOQRepository extends PagingAndSortingRepository<BOQItem, Long> {

}
