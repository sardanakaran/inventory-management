/**
 * 
 */
package com.rajcorporation.tender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajcorporation.tender.model.FileInfo;

/**
 * @author karan
 *
 */
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
	List<FileInfo> findByTenderId(Long tenderId);

}
