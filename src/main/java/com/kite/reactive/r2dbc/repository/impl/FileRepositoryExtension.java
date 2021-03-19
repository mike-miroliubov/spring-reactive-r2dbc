package com.kite.reactive.r2dbc.repository.impl;

import reactor.core.publisher.Mono;

public interface FileRepositoryExtension {
    Mono<Integer> createDataSetAssociation(long fileId, long dataSetId);
}
