package com.kite.reactive.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("dataset")
@Builder
@With
public class DataSet {
    @Id
    private Long id;
    private String name;
    @Transient
    @EqualsAndHashCode.Exclude
    private Collection<File> items;
}
