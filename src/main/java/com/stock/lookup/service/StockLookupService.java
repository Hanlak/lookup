package com.stock.lookup.service;

import com.stock.lookup.dao.LookUpRepository;
import com.stock.lookup.dto.StockLookUpDTO;
import com.stock.lookup.mapper.StockLookupMapper;
import com.stock.lookup.model.StockLookUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockLookupService {

  private final LookUpRepository lookUpRepository;
  @Autowired StockLookupMapper stockLookupMapper;

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
  public StockLookUpDTO getSuggestionBasedOnGroupandStockName(String groupName, String stockName) {
    StockLookUp stockLookUp =
        lookUpRepository
            .findByGroupNameAndStockName(groupName, stockName)
            .orElseThrow(EntityNotFoundException::new);
    return stockLookupMapper.toDto(stockLookUp);
  }

  // singleLike:
  public List<StockLookUpDTO> getSuggestionBasedOnGroupandStockNameLike(
      String groupName, String stockName) {
    List<StockLookUp> stockLookUpList =
        lookUpRepository.findByGroupNameAndStockNameContaining(groupName, stockName);
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
      @RequestBody StockLookUpDTO newStockLookUpDTO,
      @PathVariable String groupName,
      @PathVariable String stockName) {
    return lookUpRepository
        .findByGroupNameAndStockName(groupName, stockName)
        .map(
            lookUp -> {
              lookUp.setBuyStartRange(newStockLookUpDTO.buyStartRange);
              lookUp.setBuyEndRange(newStockLookUpDTO.buyEndRange);
              lookUp.setWeightAge(newStockLookUpDTO.weightAge);
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
