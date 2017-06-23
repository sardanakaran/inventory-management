/**
 * 
 */
package com.rajcorporation.tender.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajcorporation.tender.model.FileInfo;

/**
 * @author karan
 *
 */
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

}
