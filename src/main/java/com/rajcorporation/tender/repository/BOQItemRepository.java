package com.rajcorporation.tender.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rajcorporation.tender.model.BOQ;
import com.rajcorporation.tender.model.BOQItem;

public interface BOQItemRepository extends PagingAndSortingRepository<BOQItem, Long> {
	List<BOQItem> findByBoq(BOQ boq);
}
