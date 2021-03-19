package com.kite.reactive.r2dbc.repository;

import com.kite.reactive.r2dbc.entity.File;
import com.kite.reactive.r2dbc.repository.impl.FileRepositoryExtension;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface FileRepository extends ReactiveCrudRepository<File, Long>, FileRepositoryExtension {
    Mono<File> findByPath(String path);
}
