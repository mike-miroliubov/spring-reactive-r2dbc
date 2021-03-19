package com.kite.reactive.r2dbc.repository;

import com.kite.reactive.r2dbc.entity.DataSet;
import com.kite.reactive.r2dbc.repository.impl.DataSetRepositoryExtension;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DataSetRepository extends ReactiveCrudRepository<DataSet, Long>, DataSetRepositoryExtension {
}
