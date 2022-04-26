package com.stock.lookup.util;

import com.stock.lookup.dto.StockLookUpDTO;
import com.stock.lookup.model.BotAuth;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class BotMessageTemplates {

    public static String displayStockNames(List<StockLookUpDTO> stockNames) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<u><b>StockNames:</b></u>").append("\n\n");
        for (StockLookUpDTO stockLookUpDTO : stockNames) {
            stringBuilder.append(stockLookUpDTO.getStockName()).append("\n\n");
        }
        return stringBuilder.toString();
    }

    public static String displayBuyRange(List<StockLookUpDTO> buyRange) {
        if (!ObjectUtils.isEmpty(buyRange)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<u><b>StockName:</b></u>").append("\t").append("<u><b>BuyStart:</b></u>").append("\t").append("<u><b>BuyEnd:</b></u>").append("\n\n");
            for (StockLookUpDTO stockLookUpDTO : buyRange) {
                stringBuilder.append(stockLookUpDTO.getStockName()).append("\t").append(stockLookUpDTO.getBuyStartRange()).append("\t").append(stockLookUpDTO.getBuyEndRange()).append("\n\n");
            }
            return stringBuilder.toString();
        } else return "<b> Suggestions Not Found </b>";
    }

    public static String displayShowHelp() {
        return " Use Only Below Commands with :: /"
                + "\n"
                + "\n"
                + "<b>1. /showStockNames </b>"
                + "\n"
                + "\n"
                + "<b>2. buyRangeFor#stockName</b>"
                + "\n"
                + "\n"
                + "<b>3. buyRangeLike#stockNameStartsWith</b>"
                + "\n"
                + "\n"
                + "<b>4. /buyRangeAll</b>"
                + "\n";
    }

    public static String botHelp() {
        return "<b>            MONTI!                  </b>\n" +
                "1.To See Suggestions:: /showHelp "
                + "\n"
                + "\n"
                + "2.To Add :: /addHelp "
                + "\n"
                + "\n"
                + "3.To update :: /updateHelp "
                + "\n"
                + "\n"
                + "4.To Delete :: /deleteHelp "
                + "\n"
                + "\n"
                + "5.USER :: /userHelp "
                + "\n"
                + "\n"
                + "6.BOT HELP :: /botHelp";
    }

    public static String displayAddHelp() {
        return " To add the Data Use the below Command:(Admin Only)" +
                "\n" +
                "\n" +
                "addBuyRange#stockName,(NSE or BSE or NABSE),buyStart,buyEnd" +
                "\n";
    }

    public static String displayUpdateHelp() {
        return " To update the Data Use the below Command:(Admin Only)" +
                "\n" +
                "\n" +
                "updateBuyRange#stockName,[NSE or BSE or NABSE](update),buyStart(update),buyEnd(update)" +
                "\n";
    }

    public static String displayDeleteHelp() {
        return " To update the Data Use the below Command:(Admin Only)" +
                "\n" +
                "\n" +
                "deleteBuyRange#stockName" +
                "\n";
    }

    public static String displayUserHelp() {
        return "1. /register => to Register for Admin Access( One Admin Only Allowed )" +
                "\n" +
                "\n" +
                "2. /unregister => to UnRegister for Admin Access(Others Can Register to Gain Access)" +
                "\n" +
                "\n" +
                "3. /getGroupId => To get the GroupId That You have Admin Access For" +
                "\n" +
                "\n" +
                "4. migrateFrom#GroupId of the Data you want from( Should be the Admin for the old group as Well" +
                "\n";
    }

    public static String displayGroupIds(List<BotAuth> botAuths) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b> You Have Admin Access For:: </b>").append("\n\n");
        for (BotAuth botAuth : botAuths) {
            stringBuilder.append(botAuth.getGroupName())
                    .append("   ::  ")
                    .append(botAuth.getGroupTitle()).append("\n").append("\n");
        }
        return stringBuilder.toString();
    }

}