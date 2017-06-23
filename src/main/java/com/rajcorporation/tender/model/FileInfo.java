/**
 * 
 */
package com.rajcorporation.tender.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * @author karan
 *
 */
@Entity
@Data
public class FileInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	String name;

	String path;

	@Temporal(TemporalType.TIMESTAMP)
	Date uploadedAt;

	String uploadedBy;

}
