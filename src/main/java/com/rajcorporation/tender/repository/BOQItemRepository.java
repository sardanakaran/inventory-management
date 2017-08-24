package com.rajcorporation.tender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.rajcorporation.tender.model.BOQItem;

public interface BOQItemRepository extends PagingAndSortingRepository<BOQItem, Long> {
    List<BOQItem> findByTenderIdAndBoqVersion(Long tenderId, int boqVersion);
    List<BOQItem> findByTenderId(Long tenderId);
}
