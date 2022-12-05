package com.nl.gerimedica.app.controller;

import com.nl.gerimedica.app.dto.ItemDto;
import com.nl.gerimedica.app.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // build
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    void uploadCSVFile() throws Exception {
        // given
        // when
        // then
    }

    @Test
    void retrieveAllItems() throws Exception {

        // given
        ItemDto itemDto = new ItemDto();
        itemDto.setCode("213456");

        ItemDto itemDto2 = new ItemDto();
        itemDto.setCode("213457");

        ItemDto itemDto3 = new ItemDto();
        itemDto.setCode("213458");

        when(itemService.retrieveAllItems()).thenReturn(List.of(itemDto, itemDto2, itemDto3));

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/item"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)));
        // then
        verify(itemService).retrieveAllItems();
    }

    @Test
    void retrieveItemByCode() throws Exception {
        // given
        ItemDto itemDto = new ItemDto();
        itemDto.setCode("213456");

        when(itemService.retrieveItemByCode(anyString())).thenReturn(itemDto);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/item/213456"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code", is(itemDto.getCode())));

        // then
        verify(itemService).retrieveItemByCode(anyString());
    }

    @Test
    void deleteAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/item"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(itemService).deleteAllItems();
    }
}