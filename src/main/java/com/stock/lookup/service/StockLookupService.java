package com.stock.lookup.service;

import com.stock.lookup.dao.LookUpRepository;
import com.stock.lookup.dto.StockLookUpDTO;
import com.stock.lookup.mapper.IStockLookupMapper;
import com.stock.lookup.model.StockLookUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockLookupService {

    private final LookUpRepository lookUpRepository;
    @Autowired
    IStockLookupMapper stockLookupMapper;

    public StockLookupService(LookUpRepository lookUpRepository) {
        this.lookUpRepository = lookUpRepository;
    }

    // Retrieve
    // list
    public List<StockLookUpDTO> getAllSuggestionsBasedONGroupName(String groupName) {
        List<StockLookUp> stockLookUps = lookUpRepository.findByGroupName(groupName);
        return stockLookUps.parallelStream().map(stockLookupMapper::toDto).collect(Collectors.toList());
    }

    // single:
    public StockLookUpDTO getSuggestionBasedOnGroupAndStockName(String groupName, String stockName) {
        StockLookUp stockLookUp =
                lookUpRepository
                        .findByGroupNameAndStockName(groupName, stockName)
                        .orElseThrow(EntityNotFoundException::new);
        return stockLookupMapper.toDto(stockLookUp);
    }

    // singleLike:
    public List<StockLookUpDTO> getSuggestionBasedOnGroupAndStockNameLike(
            String groupName, String stockName) {
        List<StockLookUp> stockLookUpList =
                lookUpRepository.findByGroupNameAndStockNameStartsWithIgnoreCase(groupName, stockName);
        return stockLookUpList
                .parallelStream()
                .map(stockLookupMapper::toDto)
                .collect(Collectors.toList());
    }

    // create
    public StockLookUpDTO createNewSuggestion(StockLookUpDTO stockLookUpDTO) {
        StockLookUp stockLookUp = stockLookupMapper.fromDto(stockLookUpDTO);
        StockLookUp saveState = lookUpRepository.save(stockLookUp);
        return stockLookupMapper.toDto(saveState);
    }

    public List<StockLookUpDTO> createNewSuggestions(List<StockLookUpDTO> stockLookUpDTOs) {
        List<StockLookUp> stockLookUps =
                stockLookUpDTOs
                        .parallelStream()
                        .map(stockLookupMapper::fromDto)
                        .collect(Collectors.toList());
        return lookUpRepository
                .saveAll(stockLookUps)
                .parallelStream()
                .map(stockLookupMapper::toDto)
                .collect(Collectors.toList());
    }

    // Update::
    public StockLookUpDTO updateOldSuggestionOrCreateNew(
            StockLookUpDTO newStockLookUpDTO,
            String groupName,
            String stockName) {
        return lookUpRepository
                .findByGroupNameAndStockName(groupName, stockName)
                .map(
                        lookUp -> {
                            lookUp.setBuyStartRange(newStockLookUpDTO.buyStartRange);
                            lookUp.setBuyEndRange(newStockLookUpDTO.buyEndRange);
                            lookUp.setCategory(newStockLookUpDTO.category);
                            StockLookUp stockLookUp = lookUpRepository.save(lookUp);
                            return stockLookupMapper.toDto(stockLookUp);
                        })
                .orElseGet(
                        () -> {
                            StockLookUp stockLookUp = stockLookupMapper.fromDto(newStockLookUpDTO);
                            StockLookUp updateSaveState = lookUpRepository.save(stockLookUp);
                            return stockLookupMapper.toDto(updateSaveState);
                        });
    }

    // deleteByGroupName
    // list
    public void deleteAllSuggestionsBasedONGroupName(String groupName) {
        List<StockLookUp> stockLookUps = lookUpRepository.findByGroupName(groupName);
        lookUpRepository.deleteAll(stockLookUps);
    }

    // single
    @Transactional
    public void deleteSuggestionBasedOnGroupandStockName(String groupName, String stockName) {
        StockLookUp stockLookUp =
                lookUpRepository
                        .findByGroupNameAndStockName(groupName, stockName)
                        .orElseThrow(EntityNotFoundException::new);
        lookUpRepository.deleteByGroupNameAndStockName(groupName, stockName);
    }
}
