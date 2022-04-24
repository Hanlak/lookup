package com.stock.lookup.util;

import com.stock.lookup.dto.StockLookUpDTO;

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
        return "<b>Please Use the Following Commands To Use ** MONTI **</b" +
                "\n" +
                "<b>TO DISPLAY BUY RANGES::" +
                "\n" +
                "/showStockName" +
                "\n" +
                "/buyRangeFor <space> nameOfTheStockStartsWith" +
                "\n" +
                "/buyRangeLike <space> nameOfTheStock" +
                "\n" +
                "/buyRangeForAll" +
                " ******** THANK YOU   *******  ";
    }

}
