package com.stock.lookup.controller;

import com.stock.lookup.dao.LookUpRepository;
import com.stock.lookup.model.StockLookUp;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
public class LookupController {
    private final LookUpRepository lookUpRepository;

    LookupController(LookUpRepository lookUpRepository) {
        this.lookUpRepository = lookUpRepository;
    }

    //Retrieve
    @GetMapping("/thestocklookup/suggestions/{groupName}")
    List<StockLookUp> getAllSuggestionsBasedONGroupName(@PathVariable String groupName) {
        return lookUpRepository.findByGroupName(groupName);
    }

    @GetMapping("/thestocklookup/suggestion/{groupName}/{stockName}")
    StockLookUp getSuggestionBasedOnGroupandStockName(@PathVariable String groupName, @PathVariable String stockName) {
        return lookUpRepository.findByGroupNameAndStockName(groupName, stockName).orElseThrow(EntityNotFoundException::new);
    }

    //Create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/thestocklookup/suggestions")
    List<StockLookUp> addNewSuggestions(@RequestBody List<StockLookUp> stockLookUps) {
        return lookUpRepository.saveAll(stockLookUps);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/thestocklookup/suggestion")
    StockLookUp addNewSuggestion(@RequestBody StockLookUp stockLookUp) {
        return lookUpRepository.save(stockLookUp);
    }

    //update
    @PutMapping("/thestocklookup/suggestion/{groupName}/{stockName}")
    StockLookUp updateTheSuggestion(@RequestBody StockLookUp newStockLookUp, @PathVariable String groupName, @PathVariable String stockName) {
        return lookUpRepository.findByGroupNameAndStockName(groupName, stockName).
                map(lookUp -> {
                            lookUp.setBuyStartRange(newStockLookUp.getBuyStartRange());
                            lookUp.setBuyEndRange(newStockLookUp.getBuyEndRange());
                            return lookUpRepository.save(lookUp);
                        }
                ).orElseGet(() -> {
            return lookUpRepository.save(newStockLookUp);
        });
    }
}
