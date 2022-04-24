package com.stock.lookup.telegram;

import com.stock.lookup.dto.StockLookUpDTO;
import com.stock.lookup.service.StockLookupService;
import com.stock.lookup.util.BotMessageTemplates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MontiService {

    @Autowired
    private  StockLookupService stockLookupService;

    public String showStockNames(String groupId){
        List<StockLookUpDTO> stockLookUpDTOS = stockLookupService.getAllSuggestionsBasedONGroupName(groupId);
        return BotMessageTemplates.displayStockNames(stockLookUpDTOS);
    }

    public String buyRangeStockNameStartsWith(String groupName,String stockName){
        List<StockLookUpDTO> stockLookUpDTOS = stockLookupService.getSuggestionBasedOnGroupAndStockNameLike(groupName,stockName);
        return BotMessageTemplates.displayBuyRange(stockLookUpDTOS);
    }

    public String buyRangeSpecificStockName(String groupName,String stockName){
        StockLookUpDTO stockLookUpDTO = stockLookupService.getSuggestionBasedOnGroupAndStockName(groupName, stockName);
        return BotMessageTemplates.displayBuyRange(Collections.singletonList(stockLookUpDTO));
    }

    public String buyRangeForAllStocks(String groupId){
        List<StockLookUpDTO> stockLookUpDTOS = stockLookupService.getAllSuggestionsBasedONGroupName(groupId);
        return BotMessageTemplates.displayBuyRange(stockLookUpDTOS);
    }


}
