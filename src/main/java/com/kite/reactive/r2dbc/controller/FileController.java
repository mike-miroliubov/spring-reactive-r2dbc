package com.kite.reactive.r2dbc.controller;

import com.kite.reactive.r2dbc.entity.File;
import com.kite.reactive.r2dbc.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/files")
    public Flux<File> getFiles() {
        return fileService.getFiles();
    }

    @PostMapping("/datasets/{dataSetId}/files")
    public Mono<File> createFile(@PathVariable long dataSetId,
                                 @RequestBody File file) {
        return fileService.createFile(file, dataSetId);
    }

    @PutMapping("/datasets/{dataSetId}/files/{fileId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> addFile(@PathVariable long dataSetId,
                                 @PathVariable long fileId) {
        return fileService.addFileToDataSet(dataSetId, fileId);
    }
}
