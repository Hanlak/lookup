package com.stock.lookup.util;

import com.stock.lookup.dto.StockLookUpDTO;
import com.stock.lookup.model.BotAuth;

import java.util.List;

public class BotMessageTemplates {

    public static String displayStockNames(List<StockLookUpDTO> stockNames) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<u><b>StockNames:</b></u>").append("\n");
        for (StockLookUpDTO stockLookUpDTO : stockNames) {
            stringBuilder.append(stockLookUpDTO.getStockName()).append("\n");
        }
        return stringBuilder.toString();
    }

    public static String displayBuyRange(List<StockLookUpDTO> buyRange) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<u><b>StockNames:</b></u>").append("\t").append("<u><b>BuyStart:</b></u>").append("\t").append("<u><b>BuyEnd:</b></u>").append("\n");
        for (StockLookUpDTO stockLookUpDTO : buyRange) {
            stringBuilder.append(stockLookUpDTO.getStockName()).append("\t").append(stockLookUpDTO.getBuyStartRange()).append("\t").append(stockLookUpDTO.getBuyEndRange()).append("\n");
        }
        return stringBuilder.toString();
    }

    public static String displayHelp() {
        return " Use Only Below Commands with :: /"
                + "\n"
                + "<b>1. showStockNames</b>"
                + "\n"
                + "<b>2. buyRangeFor space stockName</b>"
                + "\n"
                + "<b>3. buyRangeLike space stockNameStartsWith</b>"
                + "\n"
                + "<b>4. buyRangeAll</b>"
                + "\n"
                + "<b>             Thank You      </b>";
    }

    public static String displayGroupIds(List<BotAuth> botAuths) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b> GroupIds That You have Write Access</b>").append("\n");
        for (BotAuth botAuth : botAuths) {
            stringBuilder.append(botAuth.getGroupName())
                    .append("\n")
                    .append(botAuth.getGroupTitle());
        }
        return stringBuilder.toString();
    }

}