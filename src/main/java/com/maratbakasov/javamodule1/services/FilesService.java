package com.maratbakasov.javamodule1.services;

import com.maratbakasov.javamodule1.db.FileRepository;
import com.maratbakasov.javamodule1.models.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class FilesService {
    private static final Logger logger = LoggerFactory.getLogger(FilesService.class);

    private final FileRepository fileRepository;

    public FilesService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Async
    public CompletableFuture<List<FileInfo>> getAll() {
        try {
            return CompletableFuture.completedFuture(fileRepository.findAll());
        } catch (DataAccessException e) {
            logger.error("Error retrieving all files", e);
            return CompletableFuture.completedFuture(Collections.emptyList());
        }
    }

    @Async
    public CompletableFuture<Optional<FileInfo>> getById(int id) {
        try {
            return CompletableFuture.completedFuture(Optional.ofNullable(fileRepository.findById(id)));
        } catch (DataAccessException e) {
            logger.error("Error retrieving file with id: " + id, e);
            return CompletableFuture.completedFuture(Optional.empty());
        }
    }

    @Async
    public CompletableFuture<Boolean> save(FileInfo file) {
        try {
            byte[] contents = file.getContents();
            if (contents == null) {
                contents = readFile(file.getPath());
            }
            fileRepository.save(new FileInfo(file.getId(), file.getPath(), contents));
            return CompletableFuture.completedFuture(true);
        } catch (IOException e) {
            logger.error("Error reading file from path: " + file.getPath(), e);
        } catch (DataAccessException e) {
            logger.error("Error saving file to database", e);
        }
        return CompletableFuture.completedFuture(false);
    }

    @Async
    public CompletableFuture<Boolean> delete(int id) {
        try {
            return CompletableFuture.completedFuture(fileRepository.deleteById(id) > 0);
        } catch (DataAccessException e) {
            logger.error("Error deleting file with id: " + id, e);
            return CompletableFuture.completedFuture(false);
        }
    }

    private byte[] readFile(String path) throws IOException {
        var bytes = Files.readAllBytes(Paths.get(path));
        logger.info("{}", bytes);
        return bytes;
    }
}
