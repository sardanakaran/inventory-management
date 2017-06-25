/**
 * 
 */
package com.rajcorporation.tender.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author karan
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rajcorporation.tender.exception.StorageException;
import com.rajcorporation.tender.exception.StorageFileNotFoundException;
import com.rajcorporation.tender.repository.StorageProperties;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

	private final Path base;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.base = Paths.get(properties.getLocation());
		if (!Files.exists(base)) {
			init();
		}
	}

	@Override
	public String store(MultipartFile file, String folder) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
			}

			Path relativePath = Paths.get(folder, UUID.randomUUID().toString()).resolve(file.getOriginalFilename());
			Path directory = Files.createDirectories(this.base.resolve(relativePath));
			Files.copy(file.getInputStream(), directory, StandardCopyOption.REPLACE_EXISTING);

			return relativePath.toString();

		} catch (IOException e) {
			throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.base, 1).filter(path -> !path.equals(this.base))
					.map(path -> this.base.relativize(path));
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String path) {
		return base.resolve(path);
	}

	@Override
	public Resource loadAsResource(String path) {
		try {
			Path file = load(path);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + path);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + path, e);
		}
	}

	@Override
	public void init() {
		try {
			Files.createDirectory(base);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public boolean remove(String path) {
		Path directory = this.base.resolve(path).getParent();
		try {
			Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}
			});

			return !Files.exists(this.base.resolve(Paths.get(path)));
		} catch (IOException e) {
			//log.error("Couldn't delete the file", e);
			return false;
		}
	}
}
