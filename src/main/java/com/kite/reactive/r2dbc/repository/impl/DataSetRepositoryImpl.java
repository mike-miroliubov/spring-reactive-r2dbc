package com.kite.reactive.r2dbc.repository.impl;

import com.kite.reactive.r2dbc.entity.DataSet;
import com.kite.reactive.r2dbc.entity.File;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

/**
 * Have to create a repository implementation because Spring Data R2DBC does not support relationships
 */
@RequiredArgsConstructor
public class DataSetRepositoryImpl implements DataSetRepositoryExtension {
    private final DatabaseClient db;

    @Override
    public Flux<DataSet> findAll() {
        return db.sql("SELECT ds.id as dataset_id, ds.name as dataset_name, f.id as file_id, f.path as file_path " +
                "FROM dataset ds " +
                "JOIN dataset_file dsf ON dsf.dataset_id = ds.id " +
                "JOIN file f ON dsf.file_id = f.id")
                .map(r -> {
                    var file = new File(r.get("file_id", Long.class), r.get("file_path", String.class));
                    var dataSet = DataSet.builder()
                            .id(r.get("dataset_id", Long.class))
                            .name(r.get("dataset_name", String.class))
                            .build();
                    return Tuples.of(dataSet, file);
                })
                .all()
                .collectMultimap(Tuple2::getT1, Tuple2::getT2)
                .flatMapMany(map -> Flux.fromStream(map.entrySet()
                        .stream()
                        .map(pair -> pair.getKey().withItems(pair.getValue()))));

    }
}
