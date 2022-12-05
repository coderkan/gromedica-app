package com.nl.gerimedica.app.util;

import com.nl.gerimedica.app.entity.ItemEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CSVUtilTest {

    @Test
    void parseToEntity() throws IOException {
        // given
        CSVUtil csvUtil = new CSVUtil();

        File file = new File("exercise.csv");
        InputStream inputStream = new FileInputStream(file);

        MultipartFile multipartFile = new MockMultipartFile("exercise.csv", "", "text/csv", inputStream);

        // when
        List<ItemEntity> items = csvUtil.parseToEntity(multipartFile);

        // then
        assertThat(items).isNotNull();
        assertThat(items).hasSizeGreaterThan(1);

    }
}