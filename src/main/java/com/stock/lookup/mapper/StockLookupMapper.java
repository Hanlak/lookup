package com.stock.lookup.mapper;

import com.stock.lookup.dto.StockLookUpDTO;
import com.stock.lookup.model.StockLookUp;
import org.springframework.stereotype.Component;

@Component
public class StockLookupMapper {

  public StockLookUp fromDto(StockLookUpDTO stockLookUpDTO) {
    return new StockLookUp(
            stockLookUpDTO.groupName,
            stockLookUpDTO.stockName,
            stockLookUpDTO.weightAge,
            stockLookUpDTO.buyStartRange,
            stockLookUpDTO.buyEndRange);
  }

  public StockLookUpDTO toDto(StockLookUp stockLookUp) {
    return new StockLookUpDTO(
            stockLookUp.getGroupName(),
            stockLookUp.getStockName(),
            stockLookUp.getWeightAge(),
            stockLookUp.getBuyStartRange(),
            stockLookUp.getBuyEndRange());
  }
}
