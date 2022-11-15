package com.nl.gerimedica.app.controller;

import com.nl.gerimedica.app.dto.ItemDto;
import com.nl.gerimedica.app.exception.UnsupportedFileFormatException;
import com.nl.gerimedica.app.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<List<ItemDto>> uploadCSVFile(@RequestParam("file") MultipartFile csvFile) throws UnsupportedFileFormatException {
        return ResponseEntity.ok(itemService.uploadFileToDatabase(csvFile));
    }

    @GetMapping
    public  ResponseEntity< List<ItemDto>> retrieveAllItems(){
        return ResponseEntity.ok(itemService.retrieveAllItems());
    }

    @GetMapping("/{code}")
    public ResponseEntity<ItemDto> retrieveItemByCode(@PathVariable("code") String code){
        return ResponseEntity.ok(itemService.retrieveItemByCode(code));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
        itemService.deleteAllItems();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
