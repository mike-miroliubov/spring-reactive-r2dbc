package com.kite.reactive.r2dbc.service;

import com.kite.reactive.r2dbc.entity.File;
import com.kite.reactive.r2dbc.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final DataSetService dataSetService;

    private final ReactiveTransactionManager transactionManager;

    public Flux<File> getFiles() {
        return fileRepository.findAll();
    }

    /**
     * Declarative transaction management
     */
    @Transactional
    public Mono<File> createFile(File file, long dataSetId) {
        // Everything runs in 1 transaction - on 1 connection
        var dataSetMono = dataSetService.getDataSet(dataSetId);

        var newFileOrError = fileRepository.findByPath(file.getPath())
                .flatMap(f -> Mono.error(new IllegalArgumentException("File with path " + file.getPath() + " already exists")))
                .switchIfEmpty(Mono.just(file));

        return dataSetMono
                .zipWith(newFileOrError) // will fail if error
                .flatMap(it -> {
                    var dataSet = it.getT1();
                    return fileRepository.save(file)
                            .flatMap(f -> fileRepository.createDataSetAssociation(f.getId(), dataSet.getId())
                                    .then(Mono.just(f)));
                });
    }

    /**
     * Programmatic transaction management
     */
    public Mono<Void> addFileToDataSet(long dataSetId, long fileId) {
        // these two queries run without a transaction, on separate connections (in parallel)
        var dataSetMono = dataSetService.getDataSet(dataSetId);
        var fileMono = fileRepository.findById(fileId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("File with id " + fileId + " not found")));

        var tx = TransactionalOperator.create(transactionManager);

        return fileMono
                .zipWith(dataSetMono)
                // This query runs in a transaction, on a separate connection
                .flatMap(fileAndDataSet -> fileRepository.createDataSetAssociation(
                        fileAndDataSet.getT1().getId(),
                        fileAndDataSet.getT2().getId())
                    .as(tx::transactional)
                )
                .then();
    }
}
