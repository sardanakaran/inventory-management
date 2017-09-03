/**
 * 
 */
package com.rajcorporation.tender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajcorporation.tender.model.BOQItem;
import com.rajcorporation.tender.model.DataInspection;

/**
 * @author Karan
 *
 */
public interface DataInspectionRepository extends JpaRepository<DataInspection, Long> {

	List<DataInspection> findByBoqItem(BOQItem boqItem);

}
