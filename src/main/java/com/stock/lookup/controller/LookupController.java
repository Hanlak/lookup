package com.stock.lookup.controller;

import com.stock.lookup.dto.StockLookUpDTO;
import com.stock.lookup.service.StockLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static com.stock.lookup.util.RequestValidator.validatePathVariables;

@RestController
@CrossOrigin
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
            @PathVariable @NotBlank String groupName) {
        validatePathVariables(groupName, "group_name");
        List<StockLookUpDTO> stockLookUpDTOList =
                stockLookupService.getAllSuggestionsBasedONGroupName(groupName);
        if (stockLookUpDTOList.isEmpty()) {
            return new ResponseEntity<>(stockLookUpDTOList, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stockLookUpDTOList, HttpStatus.OK);
    }

    @GetMapping("/thestocklookup/find/suggestion/{groupName}/{stockName}")
    ResponseEntity<StockLookUpDTO> getSuggestionByGroupandStockName(
            @PathVariable @NotBlank String groupName, @PathVariable @NotBlank String stockName) {
        validatePathVariables(groupName, "group_name", stockName, "stock_name");
        StockLookUpDTO stockLookUpDTO =
                stockLookupService.getSuggestionBasedOnGroupandStockName(groupName, stockName);
        return new ResponseEntity<>(stockLookUpDTO, HttpStatus.OK);
    }

    @GetMapping("/thestocklookup/findlike/suggestions/{groupName}/{stockName}")
    ResponseEntity<List<StockLookUpDTO>> getSuggestionByGroupandStockNameLike(
            @PathVariable @NotBlank String groupName, @PathVariable @NotBlank String stockName) {
        validatePathVariables(groupName, "group_name", stockName, "stock_name");
        List<StockLookUpDTO> stockLookUpDTOList =
                stockLookupService.getSuggestionBasedOnGroupandStockNameLike(groupName, stockName);
        if (stockLookUpDTOList.isEmpty()) {
            return new ResponseEntity<>(stockLookUpDTOList, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stockLookUpDTOList, HttpStatus.OK);
    }

    // Create
    @PostMapping("/thestocklookup/create/suggestions")
    ResponseEntity<List<StockLookUpDTO>> addNewSuggestions(
            @Valid @RequestBody List<StockLookUpDTO> stockLookUpDtos) {
        List<StockLookUpDTO> stockLookUpDTOList =
                stockLookupService.createNewSuggestions(stockLookUpDtos);
        return new ResponseEntity<>(stockLookUpDTOList, HttpStatus.CREATED);
    }

    @PostMapping("/thestocklookup/create/suggestion")
    ResponseEntity<StockLookUpDTO> addNewSuggestion(
            @Valid @RequestBody StockLookUpDTO stockLookUpDTO) {
        StockLookUpDTO stockLookUpDtoSaved = stockLookupService.createNewSuggestion(stockLookUpDTO);
        return new ResponseEntity<>(stockLookUpDtoSaved, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/thestocklookup/update/suggestion/{groupName}/{stockName}")
    ResponseEntity<StockLookUpDTO> updateTheSuggestion(
            @Valid @RequestBody StockLookUpDTO newStockLookUpDTO,
            @PathVariable @NotBlank String groupName,
            @PathVariable @NotBlank String stockName) {
        validatePathVariables(groupName, "group_name", stockName, "stock_name");
        StockLookUpDTO stockLookUpDTO =
                stockLookupService.updateOldSuggestionOrCreateNew(newStockLookUpDTO, groupName, stockName);
        return new ResponseEntity<>(stockLookUpDTO, HttpStatus.ACCEPTED);
    }

    // delete
    @DeleteMapping("/thestocklookup/delete/suggestion/{groupName}/{stockName}")
    public ResponseEntity<String> deleteByGroupNameAndStockName(
            @PathVariable @NotBlank String groupName, @PathVariable @NotBlank String stockName) {
        validatePathVariables(groupName, "group_name", stockName, "stock_name");
        stockLookupService.deleteSuggestionBasedOnGroupandStockName(groupName, stockName);
        return new ResponseEntity<>("SuccessFully Deleted", HttpStatus.OK);
    }

    @DeleteMapping("/thestocklookup/delete/suggestions/{groupName}")
    public ResponseEntity<String> deleteByGroupName(@PathVariable @NotBlank String groupName) {
        validatePathVariables(groupName, "group_name");
        stockLookupService.deleteAllSuggestionsBasedONGroupName(groupName);
        return new ResponseEntity<>("SuccessFully Deleted", HttpStatus.OK);
    }
}
