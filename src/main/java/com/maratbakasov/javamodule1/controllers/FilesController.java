package com.maratbakasov.javamodule1.controllers;

import org.springframework.web.bind.annotation.*;

import com.maratbakasov.javamodule1.models.FileInfo;
import com.maratbakasov.javamodule1.services.FilesService;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/files")
public class FilesController {
	private final FilesService filesService;

	public FilesController(FilesService filesService) {
		this.filesService = filesService;
	}

	@GetMapping
	@Async
	public CompletableFuture<ResponseEntity<List<FileInfo>>> getAllFiles() {
		return filesService.getAll()
				.thenApply(ResponseEntity::ok)
				.exceptionally(e -> ResponseEntity.internalServerError().build());
	}

	@GetMapping("/{id}")
	@Async
	public CompletableFuture<ResponseEntity<FileInfo>> getFileById(@PathVariable int id) {
		return filesService.getById(id)
				.thenApply(fileInfo -> fileInfo.map(ResponseEntity::ok)
						.orElseGet(() -> ResponseEntity.notFound().build()))
				.exceptionally(e -> ResponseEntity.internalServerError().build());
	}

	@PostMapping
	@Async
	public CompletableFuture<ResponseEntity<String>> saveFile(@RequestBody FileInfo fileInfo) {
		return filesService.save(fileInfo)
				.thenApply(success -> success 
						? ResponseEntity.ok("File saved")
						: ResponseEntity.ok("Could not save file"))
				.exceptionally(e -> ResponseEntity.internalServerError().body("Error saving file"));
	}

	@DeleteMapping("/{id}")
	@Async
	public CompletableFuture<ResponseEntity<String>> deleteFile(@PathVariable int id) {
		return filesService.delete(id)
				.thenApply(success -> success
						? ResponseEntity.ok("File deleted")
						: ResponseEntity.ok("No files to delete"))
				.exceptionally(e -> ResponseEntity.internalServerError().body("Error deleting file"));
	}
}
