package com.kite.reactive.r2dbc.service;

import com.kite.reactive.r2dbc.entity.DataSet;
import com.kite.reactive.r2dbc.repository.DataSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DataSetService {
    private final DataSetRepository dataSetRepository;

    public Flux<DataSet> getAllDataSets() {
        return dataSetRepository.findAll();
    }

    public Mono<DataSet> getDataSet(long id) {
        return dataSetRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Dataset with id " + id + "not found")));
    }

    @Transactional
    public Mono<DataSet> createDataSet(DataSet dataSet) {
        return dataSetRepository.save(dataSet);
    }
}
