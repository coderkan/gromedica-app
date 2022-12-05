package com.nl.gerimedica.app.controller;

import com.nl.gerimedica.app.entity.ItemEntity;
import com.nl.gerimedica.app.exception.NotFoundEntityException;
import com.nl.gerimedica.app.repository.ItemRepository;
import com.nl.gerimedica.app.util.CSVUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ItemControllerITTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @BeforeEach
    void beforeEach() throws Exception {
        System.out.println("BEFORE-EACH");
        // load test data to the db
        CSVUtil csvUtil = new CSVUtil();

        File file = new File("exercise.csv");
        InputStream inputStream = new FileInputStream(file);

        MultipartFile multipartFile = new MockMultipartFile("exercise.csv", "", "text/csv", inputStream);

        // when
        List<ItemEntity> items = csvUtil.parseToEntity(multipartFile);
        // clear
        itemRepository.deleteAll();
        // load data
        itemRepository.saveAll(items);
    }

    @Test
    void uploadCSVFile() throws Exception {

        /*
        File file = new File("exercise.csv");
        InputStream inputStream = new FileInputStream(file);

        MockMultipartFile multipartFile = new MockMultipartFile("exercise.csv", "", "text/csv", inputStream);

        mockMvc.perform( //
                multipart("/item") //
                        .file(multipartFile) //
        ).andExpect(status().isOk());
        */

    }

    @Test
    void retrieveAllItems() throws Exception {
        this.mockMvc.perform(get("/item")) //
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$.length()", is(18))) //
                .andDo(print());
    }

    @Test
    void retrieveItemByCode() throws Exception {
        String expectedCode = "271636001";
        String expectedDisplayValue = "Polsslag regelmatig";

        this.mockMvc.perform(get("/item/" + expectedCode)) //
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$.code", is(expectedCode))) //
                .andExpect(jsonPath("$.displayValue", is(expectedDisplayValue))) //
                .andDo(print());
    }

    @Test
    void testRetrieveItemByCodeShouldThrowNotFoundEntityException() throws Exception {
        String expectedCode = "271636001345";
        this.mockMvc.perform(get("/item/" + expectedCode)) //
                .andExpect(status().isBadRequest()) //
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundEntityException)) //
                .andExpect(result -> assertEquals("Not Found Entity For Code: " + expectedCode, result.getResolvedException().getMessage())) //
                .andDo(print());
    }

    @Test
    void deleteAll() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/item"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}