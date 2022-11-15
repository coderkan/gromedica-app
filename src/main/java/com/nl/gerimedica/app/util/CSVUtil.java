package com.nl.gerimedica.app.util;

import com.nl.gerimedica.app.entity.ItemEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVUtil {
    public static final String CSV_CONTENT_TYPE = "text/csv";

    /***
     * Read csv files and create list of item entities.
     * @param file the file of csv
     * @return list of the item entities
     */
    public List<ItemEntity> parseToEntity(MultipartFile file) {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8")); org.apache.commons.csv.CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<ItemEntity> entities = new ArrayList<>();

            csvParser.getRecords() //
                    .stream() //
                    .forEach(row -> {
                        try {

                            ItemEntity itemEntity = new ItemEntity( //
                                    null,  //
                                    row.get("source"), //
                                    row.get("codeListCode"), //
                                    row.get("displayValue"),  //
                                    row.get("code"),  //
                                    row.get("longDescription"), //
                                    Utils.parseDate(row.get("fromDate")),  //
                                    Utils.parseDate(row.get("toDate")), //
                                    Utils.parseInteger(row.get("sortingPriority")) //
                            );


                            entities.add(itemEntity);
                        } catch (Exception e) {
                        }
                    });
            return entities;
        } catch (IOException e) {
            throw new RuntimeException("failed to parse CSV file: " + e.getMessage());
        }
    }


}
