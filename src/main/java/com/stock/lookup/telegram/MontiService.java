package com.stock.lookup.telegram;

import com.stock.lookup.dto.StockLookUpDTO;
import com.stock.lookup.service.StockLookupService;
import com.stock.lookup.util.BotMessageTemplates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

@Component
public class MontiService {

    @Autowired
    private StockLookupService stockLookupService;

    public String showStockNames(String groupId) {
        List<StockLookUpDTO> stockLookUpDTOS = stockLookupService.getAllSuggestionsBasedONGroupName(groupId);
        return BotMessageTemplates.displayStockNames(stockLookUpDTOS);
    }

    public String buyRangeStockNameStartsWith(String groupName, String stockName) {
        List<StockLookUpDTO> stockLookUpDTOS = stockLookupService.getSuggestionBasedOnGroupAndStockNameLike(groupName, stockName);
        return BotMessageTemplates.displayBuyRange(stockLookUpDTOS);
    }

    public String buyRangeSpecificStockName(String groupName, String stockName) {
        StockLookUpDTO stockLookUpDTO = stockLookupService.getSuggestionBasedOnGroupAndStockName(groupName, stockName);
        return BotMessageTemplates.displayBuyRange(Collections.singletonList(stockLookUpDTO));
    }

    public String buyRangeForAllStocks(String groupId) {
        List<StockLookUpDTO> stockLookUpDTOS = stockLookupService.getAllSuggestionsBasedONGroupName(groupId);
        return BotMessageTemplates.displayBuyRange(stockLookUpDTOS);
    }

    public String addBuyRangeSuggestion(String groupName, String stockName, String category, String buyStart, String buyEnd) {
        try {
            StockLookUpDTO stockLookUpDTO = new StockLookUpDTO(groupName, stockName, category, Float.parseFloat(buyStart), Float.parseFloat(buyEnd));
            StockLookUpDTO stockLookUpDTO1 = stockLookupService.createNewSuggestion(stockLookUpDTO);
            if (ObjectUtils.isEmpty(stockLookUpDTO1) || ObjectUtils.isEmpty(stockLookUpDTO1.getStockName())) {
                return "<b> Adding the suggestion" + stockName + "Failed. please Try again</b>";
            }
            return "Suggestion Added..:)";
        } catch (Exception e) {
            return "Failed to add suggestion..please try again";
        }
    }

    public String updateBuyRangeSuggestion(String groupName, String stockName, String category, String buyStart, String buyEnd) {
        try {
            StockLookUpDTO stockLookUpDTO = new StockLookUpDTO(groupName, stockName, category, Float.parseFloat(buyStart), Float.parseFloat(buyEnd));
            StockLookUpDTO stockLookUpDTO1 = stockLookupService.updateOldSuggestionOrCreateNew(stockLookUpDTO, groupName, stockName);
            if (ObjectUtils.isEmpty(stockLookUpDTO1) || ObjectUtils.isEmpty(stockLookUpDTO1.getStockName())) {
                return "<b> updating the suggestion " + stockName + "Failed. please Try again</b>";
            }
            return "Suggestion Updated..:)";
        } catch (Exception e) {
            return "Failed to update suggestion..please try again";
        }
    }

    public String deleteBuyRangeSuggestion(String groupName, String stockName) {
        try {
            stockLookupService.deleteSuggestionBasedOnGroupandStockName(groupName, stockName);
            return "All Suggestion Deleted..:)";
        } catch (Exception e) {
            return "Failed to Delete suggestions..please try again";
        }
    }
}
