package com.stock.lookup.controller;

import com.stock.lookup.dto.StockLookUpDTO;
import com.stock.lookup.service.CsvBatchLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class CsvBatchLookupController {
    @Autowired
    CsvBatchLookupService csvBatchLookupService;

    @PostMapping(value = "/thestocklookup/csvbatch/suggestions", consumes = "multipart/form-data")
    public ResponseEntity<List<StockLookUpDTO>> createSuggestionFromCSV(
            @RequestParam("file") MultipartFile file) {
        List<StockLookUpDTO> stockLookUpDTOList =
                csvBatchLookupService.createNewSuggestionsBasedOnCSV(file);
        return new ResponseEntity<>(stockLookUpDTOList, HttpStatus.CREATED);
    }
}
