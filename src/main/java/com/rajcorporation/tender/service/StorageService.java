/**
 * 
 */
package com.rajcorporation.tender.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author karan
 *
 */
public interface StorageService {

	String store(MultipartFile file, String folder);

	Path load(String path);

	Stream<Path> loadAll();

	Resource loadAsResource(String path);

	void init();

	boolean remove(String path);

}
