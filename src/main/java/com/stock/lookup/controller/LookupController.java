package com.stock.lookup.controller;

import com.stock.lookup.dao.LookUpRepository;
import com.stock.lookup.dto.StockLookUpDTO;
import com.stock.lookup.service.StockLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LookupController {
  private final LookUpRepository lookUpRepository;
  @Autowired
  StockLookupService stockLookupService;

  LookupController(LookUpRepository lookUpRepository) {
    this.lookUpRepository = lookUpRepository;
  }


  @GetMapping("/thestocklookup/health")
  public String checkHealth() {
    return "Health OK";
  }

  // Retrieve
  @GetMapping("/thestocklookup/suggestions/{groupName}")
  List<StockLookUpDTO> getAllSuggestionsByGroupName(@PathVariable String groupName) {
    return stockLookupService.getAllSuggestionsBasedONGroupName(groupName);
  }

  @GetMapping("/thestocklookup/suggestion/{groupName}/{stockName}")
  StockLookUpDTO getSuggestionByGroupandStockName(
          @PathVariable String groupName, @PathVariable String stockName) {
    return stockLookupService.getSuggestionBasedOnGroupandStockName(groupName, stockName);
  }

  // Create
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/thestocklookup/suggestions")
  List<StockLookUpDTO> addNewSuggestions(@RequestBody List<StockLookUpDTO> stockLookUpDtos) {
    return stockLookupService.createNewSuggestions(stockLookUpDtos);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/thestocklookup/suggestion")
  StockLookUpDTO addNewSuggestion(@RequestBody StockLookUpDTO stockLookUpDTO) {
    return stockLookupService.createNewSuggestion(stockLookUpDTO);
  }

  // update
  @PutMapping("/thestocklookup/suggestion/{groupName}/{stockName}")
  StockLookUpDTO updateTheSuggestion(
          @RequestBody StockLookUpDTO newStockLookUpDTO,
          @PathVariable String groupName,
          @PathVariable String stockName) {
    return stockLookupService.updateOldSuggestionOrCreateNew(
            newStockLookUpDTO, groupName, stockName);
  }

  // delete
  @DeleteMapping("/thestocklookup/suggestion/{groupName}/{stockName}")
  public void deleteByGroupNameAndStockName(
          @PathVariable String groupName, @PathVariable String stockName) {
    stockLookupService.deleteSuggestionBasedOnGroupandStockName(groupName, stockName);
  }

  @DeleteMapping("/thestocklookup/suggestions/{groupName}")
  public void deleteByGroupName(@PathVariable String groupName) {
    stockLookupService.deleteAllSuggestionsBasedONGroupName(groupName);
  }
}
