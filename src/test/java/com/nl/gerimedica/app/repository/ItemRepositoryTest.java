package com.nl.gerimedica.app.repository;

import com.nl.gerimedica.app.entity.ItemEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItemRepositoryTest {

    @Container
    MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:latest")
            .withDatabaseName("spring-reddit-test-db")
            .withUsername("testuser")
            .withPassword("pass");

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void testShouldSaveItem() {

        ItemEntity itemEntity = ItemEntity //
                .builder() //
                .code("271636001") //
                .codeListCode("ZIB001") //
                .displayValue("Polsslag regelmatig") //
                .longDescription("The long description is necessary") //
                .sortingPriority(1) //
                .toDate(null) //
                .fromDate(LocalDate.parse("2019-01-01")) //
                .source("ZIB") //
                .build();

        ItemEntity savedItemEntity = itemRepository.save(itemEntity);

        assertThat(savedItemEntity).usingRecursiveComparison().ignoringFields("id").isNotNull();
        assertThat(savedItemEntity.getCode()).isEqualTo(itemEntity.getCode());
    }

    @Test
    void findByCode() {


        ItemEntity itemEntity = ItemEntity //
                .builder() //
                .code("271636002") //
                .codeListCode("ZIB002") //
                .displayValue("Polsslag regelmatig2") //
                .longDescription("The long description is necessary2") //
                .sortingPriority(1) //
                .toDate(null) //
                .fromDate(LocalDate.parse("2019-02-02")) //
                .source("ZIB") //
                .build();
        ItemEntity savedItemEntity = itemRepository.save(itemEntity);

        assertThat(savedItemEntity).usingRecursiveComparison().ignoringFields("id").isNotNull();
        assertThat(savedItemEntity.getCode()).isEqualTo(itemEntity.getCode());

        //
        String expectedCode = "271636002";
        Optional<ItemEntity> optionalItemEntity = itemRepository.findByCode(expectedCode);
        assertThat(optionalItemEntity).isNotEmpty();
        ItemEntity retrievedItemEntity = optionalItemEntity.get();
        assertThat(retrievedItemEntity.getCode()).isEqualTo(itemEntity.getCode());
        assertThat(retrievedItemEntity.getCodeListCode()).isEqualTo(itemEntity.getCodeListCode());
        assertThat(retrievedItemEntity.getDisplayValue()).isEqualTo(itemEntity.getDisplayValue());
        assertThat(retrievedItemEntity.getLongDescription()).isEqualTo(itemEntity.getLongDescription());
    }

}