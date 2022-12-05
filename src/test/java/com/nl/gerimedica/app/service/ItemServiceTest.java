package com.nl.gerimedica.app.service;

import com.nl.gerimedica.app.dto.ItemDto;
import com.nl.gerimedica.app.entity.ItemEntity;
import com.nl.gerimedica.app.exception.UnsupportedFileFormatException;
import com.nl.gerimedica.app.repository.ItemRepository;
import com.nl.gerimedica.app.util.CSVUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CSVUtil csvUtil;

    @InjectMocks
    private ItemService itemService;

    @Test
    void testUploadFileToDatabaseShouldThrowUnsupportedFileFormatException() {
        // given
        MultipartFile multipartFile = new MockMultipartFile("sample-csv-request", "", "text/pdf", new byte[]{});

        // when
        assertThrows(UnsupportedFileFormatException.class, () -> {
            itemService.uploadFileToDatabase(multipartFile);
        });
    }

    @Test
    void uploadFileToDatabase() {
        // given
        MultipartFile multipartFile = new MockMultipartFile("sample-csv-request", "", "text/csv", new byte[]{});

        ItemEntity itemEntity = ItemEntity //
                .builder() //
                .id(1L) //
                .code("271636001") //
                .codeListCode("ZIB001") //
                .displayValue("Polsslag regelmatig") //
                .longDescription("The long description is necessary") //
                .sortingPriority(1) //
                .toDate(null) //
                .fromDate(LocalDate.parse("2019-01-01")) //
                .source("ZIB") //
                .build();

        ItemEntity itemEntity2 = ItemEntity //
                .builder() //
                .id(1L) //
                .code("271636002") //
                .codeListCode("ZIB002") //
                .displayValue("Polsslag regelmatig2") //
                .longDescription("The long description is necessary2") //
                .sortingPriority(1) //
                .toDate(null) //
                .fromDate(LocalDate.parse("2019-02-02")) //
                .source("ZIB") //
                .build();


        when(csvUtil.parseToEntity(any(MultipartFile.class))).thenReturn(List.of(itemEntity, itemEntity2));
        when(itemRepository.saveAll(anyList())).thenReturn(List.of(itemEntity, itemEntity2));

        // when
        List<ItemDto> items = itemService.uploadFileToDatabase(multipartFile);

        // then
        assertThat(items).isNotNull();
        assertThat(items).hasSize(2);
        verify(csvUtil).parseToEntity(any(MultipartFile.class));
        verify(itemRepository).saveAll(anyList());
    }

    @Test
    void retrieveAllItems() {
        // given
        ItemEntity itemEntity = ItemEntity //
                .builder() //
                .id(1L) //
                .code("271636001") //
                .codeListCode("ZIB001") //
                .displayValue("Polsslag regelmatig") //
                .longDescription("The long description is necessary") //
                .sortingPriority(1) //
                .toDate(null) //
                .fromDate(LocalDate.parse("2019-01-01")) //
                .source("ZIB") //
                .build();

        ItemEntity itemEntity2 = ItemEntity //
                .builder() //
                .id(1L) //
                .code("271636002") //
                .codeListCode("ZIB002") //
                .displayValue("Polsslag regelmatig2") //
                .longDescription("The long description is necessary2") //
                .sortingPriority(1) //
                .toDate(null) //
                .fromDate(LocalDate.parse("2019-02-02")) //
                .source("ZIB") //
                .build();

        when(itemRepository.findAll()).thenReturn(List.of(itemEntity, itemEntity2));

        // when
        List<ItemDto> items = itemService.retrieveAllItems();

        // then
        verify(itemRepository).findAll();
        assertThat(items).hasSize(2);
    }

    @Test
    void retrieveItemByCode() {
        // given
        ItemEntity itemEntity = ItemEntity //
                .builder() //
                .id(1L) //
                .code("271636001") //
                .codeListCode("ZIB001") //
                .displayValue("Polsslag regelmatig") //
                .longDescription("The long description is necessary") //
                .sortingPriority(1) //
                .toDate(null) //
                .fromDate(LocalDate.parse("2019-01-01")) //
                .source("ZIB") //
                .build();
        when(itemRepository.findByCode(anyString())).thenReturn(Optional.of(itemEntity));

        // when
        ItemDto itemDto = itemService.retrieveItemByCode("271636001");

        // then
        assertAll("Test All Properties of the Item Dto", () -> {
            assertThat(itemDto).isNotNull();
            assertThat(itemDto.getSource()).isEqualTo(itemEntity.getSource());
            assertThat(itemDto.getCodeListCode()).isEqualTo(itemEntity.getCodeListCode());
            assertThat(itemDto.getDisplayValue()).isEqualTo(itemEntity.getDisplayValue());
            assertThat(itemDto.getCode()).isEqualTo(itemEntity.getCode());
            assertThat(itemDto.getLongDescription()).isEqualTo(itemEntity.getLongDescription());
            assertThat(itemDto.getFromDate()).isEqualTo(itemEntity.getFromDate());
            assertThat(itemDto.getSortingPriority()).isEqualTo(itemEntity.getSortingPriority());
        });

        verify(itemRepository).findByCode(anyString());
    }

    @Test
    void deleteAllItems() {
        // given

        // when
        itemService.deleteAllItems();
        // then
        verify(itemRepository).deleteAll();
    }
}