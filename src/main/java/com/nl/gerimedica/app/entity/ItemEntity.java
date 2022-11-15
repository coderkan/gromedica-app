package com.nl.gerimedica.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(
        name = "tbl_item"
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String source;
    private String codeListCode;
    private String displayValue;
    private String code;
    private String longDescription;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer sortingPriority;
}
