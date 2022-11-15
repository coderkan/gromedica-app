package com.nl.gerimedica.app.dto;

import com.nl.gerimedica.app.entity.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private String source;
    private String codeListCode;
    private String displayValue;
    private String code;
    private String longDescription;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer sortingPriority;


    public static ItemDto toDto(ItemEntity entity) {
        ItemDto dto = new ItemDto();
        dto.setSource(entity.getSource());
        dto.setCode(entity.getCode());
        dto.setCodeListCode(entity.getCodeListCode());
        dto.setDisplayValue(entity.getDisplayValue());
        dto.setLongDescription(entity.getLongDescription());
        dto.setFromDate(entity.getFromDate());
        dto.setToDate(entity.getToDate());
        dto.setSortingPriority(entity.getSortingPriority());
        return dto;
    }
}
