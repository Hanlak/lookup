package com.stock.lookup.controller;

import com.stock.lookup.dto.StockLookUpDTO;
import com.stock.lookup.service.StockLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LookupController {

  @Autowired
  StockLookupService stockLookupService;

  @GetMapping("/thestocklookup/health")
  public ResponseEntity<String> checkHealth() {
    return new ResponseEntity<>("Health OK!!", HttpStatus.OK);
  }

  // Retrieve
  @GetMapping("/thestocklookup/find/suggestions/{groupName}")
  ResponseEntity<List<StockLookUpDTO>> getAllSuggestionsByGroupName(
          @PathVariable String groupName) {
    List<StockLookUpDTO> stockLookUpDTOList =
            stockLookupService.getAllSuggestionsBasedONGroupName(groupName);
    return new ResponseEntity<>(stockLookUpDTOList, HttpStatus.OK);
  }

  @GetMapping("/thestocklookup/find/suggestion/{groupName}/{stockName}")
  ResponseEntity<StockLookUpDTO> getSuggestionByGroupandStockName(
          @PathVariable String groupName, @PathVariable String stockName) {
    StockLookUpDTO stockLookUpDTO =
            stockLookupService.getSuggestionBasedOnGroupandStockName(groupName, stockName);
    return new ResponseEntity<>(stockLookUpDTO, HttpStatus.OK);
  }

  // Create
  @PostMapping("/thestocklookup/create/suggestions")
  ResponseEntity<List<StockLookUpDTO>> addNewSuggestions(
          @RequestBody List<StockLookUpDTO> stockLookUpDtos) {
    List<StockLookUpDTO> stockLookUpDTOList =
            stockLookupService.createNewSuggestions(stockLookUpDtos);
    return new ResponseEntity<>(stockLookUpDTOList, HttpStatus.CREATED);
  }

  @PostMapping("/thestocklookup/create/suggestion")
  ResponseEntity<StockLookUpDTO> addNewSuggestion(@RequestBody StockLookUpDTO stockLookUpDTO) {
    StockLookUpDTO stockLookUpDtoSaved = stockLookupService.createNewSuggestion(stockLookUpDTO);
    return new ResponseEntity<>(stockLookUpDtoSaved, HttpStatus.CREATED);
  }

  // update
  @PutMapping("/thestocklookup/update/suggestion/{groupName}/{stockName}")
  ResponseEntity<StockLookUpDTO> updateTheSuggestion(
          @RequestBody StockLookUpDTO newStockLookUpDTO,
          @PathVariable String groupName,
          @PathVariable String stockName) {
    StockLookUpDTO stockLookUpDTO =
            stockLookupService.updateOldSuggestionOrCreateNew(newStockLookUpDTO, groupName, stockName);
    return new ResponseEntity<>(stockLookUpDTO, HttpStatus.ACCEPTED);
  }

  // delete
  @DeleteMapping("/thestocklookup/delete/suggestion/{groupName}/{stockName}")
  public ResponseEntity<String> deleteByGroupNameAndStockName(
          @PathVariable String groupName, @PathVariable String stockName) {
    stockLookupService.deleteSuggestionBasedOnGroupandStockName(groupName, stockName);
    return new ResponseEntity<>("SuccessFully Deleted", HttpStatus.OK);
  }

  @DeleteMapping("/thestocklookup/delete/suggestions/{groupName}")
  public ResponseEntity<String> deleteByGroupName(@PathVariable String groupName) {
    stockLookupService.deleteAllSuggestionsBasedONGroupName(groupName);
    return new ResponseEntity<>("SuccessFully Deleted", HttpStatus.OK);
  }
}
