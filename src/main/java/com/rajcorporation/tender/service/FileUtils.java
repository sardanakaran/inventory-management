/**
 * 
 */
package com.rajcorporation.tender.service;

import java.util.Date;
import java.util.UUID;

import com.rajcorporation.tender.model.FileInfo;

/**
 * @author karan
 *
 */
public class FileUtils {

	public static FileInfo uploadFile(String fileName, String location) {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setName(UUID.randomUUID().toString());
		fileInfo.setUploadedAt(new Date());
		return fileInfo;
	}

	public static boolean removeFile(Long fileId, String location) {
		return true;
	}

}
