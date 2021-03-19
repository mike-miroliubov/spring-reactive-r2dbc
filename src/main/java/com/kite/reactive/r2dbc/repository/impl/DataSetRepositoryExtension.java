package com.kite.reactive.r2dbc.repository.impl;

import com.kite.reactive.r2dbc.entity.DataSet;
import reactor.core.publisher.Flux;

import java.util.List;

public interface DataSetRepositoryExtension {
    Flux<DataSet> findAll();
}
