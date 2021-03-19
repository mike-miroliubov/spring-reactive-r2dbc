package com.kite.reactive.r2dbc.controller;

import com.kite.reactive.r2dbc.entity.DataSet;
import com.kite.reactive.r2dbc.service.DataSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class DataSetController {
    private final DataSetService dataSetService;

    @GetMapping("/datasets")
    public Flux<DataSet> getAllDataSets() {
        return dataSetService.getAllDataSets();
    }

    @PostMapping("/datasets")
    public Mono<DataSet> createDataSet(@RequestBody DataSet dataSet) {
        return dataSetService.createDataSet(dataSet);
    }
}
