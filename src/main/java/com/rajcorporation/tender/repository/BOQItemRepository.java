package com.rajcorporation.tender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.rajcorporation.tender.model.BOQItem;

public interface BOQItemRepository extends PagingAndSortingRepository<BOQItem, Long> {
    @Query(value = "FROM BOQItem WHERE tender_id = :tenderId AND boq_version = :version")
    List<BOQItem> GetAllBOQItems(@Param("tenderId") Long tenderId, @Param("version") int version);
}
