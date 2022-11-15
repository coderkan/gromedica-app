package com.nl.gerimedica.app.service;


import com.nl.gerimedica.app.dto.ItemDto;
import com.nl.gerimedica.app.entity.ItemEntity;
import com.nl.gerimedica.app.exception.NotFoundEntityException;
import com.nl.gerimedica.app.exception.UnsupportedFileFormatException;
import com.nl.gerimedica.app.repository.ItemRepository;
import com.nl.gerimedica.app.util.CSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CSVUtil csvUtil;

    public List<ItemDto> uploadFileToDatabase(MultipartFile csvFile) throws UnsupportedFileFormatException {
        if (!CSVUtil.CSV_CONTENT_TYPE.equals(csvFile.getContentType()))
            throw new UnsupportedFileFormatException("Unsupported File Format");

        List<ItemEntity> itemEntities = csvUtil.parseToEntity(csvFile);
        List<ItemEntity> savedItems = itemRepository.saveAll(itemEntities);
        return savedItems //
                .stream() //
                .map(ItemDto::toDto) //
                .collect(Collectors.toList());
    }

    public List<ItemDto> retrieveAllItems() {
        return itemRepository.findAll() //
                .stream() //
                .map(ItemDto::toDto) //
                .collect(Collectors.toList());
    }

    public ItemDto retrieveItemByCode(String code) {
        ItemEntity itemEntity = itemRepository.findByCode(code) //
                .orElseThrow(() -> new NotFoundEntityException("Not Found Entity For Code: " + code));
        return ItemDto.toDto(itemEntity);
    }

    public void deleteAllItems() {
        itemRepository.deleteAll();
    }
}
