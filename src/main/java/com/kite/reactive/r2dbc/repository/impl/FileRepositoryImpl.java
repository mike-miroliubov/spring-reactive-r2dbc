package com.kite.reactive.r2dbc.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepositoryExtension {
    private final DatabaseClient db;

    @Override
    public Mono<Integer> createDataSetAssociation(long fileId, long dataSetId) {
        return db.sql("INSERT INTO dataset_file (dataset_id, file_id) VALUES ($1, $2)")
                .bind(0, dataSetId)
                .bind(1, fileId)
                .fetch()
                .rowsUpdated();
    }
}
