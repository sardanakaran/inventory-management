package com.rajcorporation.tender.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rajcorporation.tender.model.MaterialItem;

public interface MaterialRepository extends PagingAndSortingRepository<MaterialItem, Long>{

}
