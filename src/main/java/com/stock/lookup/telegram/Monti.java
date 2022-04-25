//package com.stock.lookup.telegram;
//
//import com.stock.lookup.model.BotAuth;
//import com.stock.lookup.util.BotMessageTemplates;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.ParseMode;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.util.List;
//import java.util.Locale;
//
//@Component
//public class Monti extends TelegramLongPollingBot {
//
//
//    @Autowired
//    MontiService montiService;
//    @Autowired
//    MontiAuth montiAuth;
//
//    //ADD USER NAME AND TOKEN IN ENV VAR
//    @Value("${CHAT_BOT_TOKEN}")
//    private String token;
//
//    @Value("${CHAT_BOT_USERNAME}")
//    private String userName;
//
//    @Override
//    public String getBotUsername() {
//        return userName;
//    }
//
//    @Override
//    public String getBotToken() {
//        return token;
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        SendMessage message = new SendMessage();
//        message.setParseMode(ParseMode.HTML);
//        message.enableHtml(true);
//        message.setChatId(update.getMessage().getChatId().toString());
//        message.setText(responseForUpdate(update));
//        try {
//            executeAsync(message);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String responseForUpdate(Update update) {
//        String command = update.getMessage().getText();
//        String userId = update.getMessage().getFrom().getId().toString();
//        String name = update.getMessage().getChat().getTitle();
//        //chatId is groupId;
//        //Show Command
//        String groupId = update.getMessage().getChatId().toString();
//        if (ObjectUtils.isEmpty(command) || ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(groupId)) {
//            return "<b>Monti is Having Trouble To Process your Request..:(</b>";
//        }
//        if ("/register".equalsIgnoreCase(command)) {
//            boolean register = montiAuth.registerUser(groupId, userId, name);
//            return register ? "<b>You are Registered.:)</b>" : "<b>Failed to Register</b>";
//        } else if (command.startsWith("/updateUser")) {
//            String[] split = command.split(" ");
//            if (split.length != 2) {
//                return "<b>please pass the input with predefined format with command space message </b>";
//            }
//            if (montiAuth.checkValidUser(userId, groupId)) {
//                boolean updateUser = montiAuth.updateUser(split[1], groupId);
//                return updateUser ? "<b>New User given Write Access:)</b>" : "<b>Failed to Give Write Access</b>";
//            } else {
//                return "<b> UnAuthorized to Change the Access </b>";
//            }
//        } else if ("/getGroupId".equalsIgnoreCase(command)) {
//            List<BotAuth> botAuths = montiAuth.getGroupIds(userId);
//            return "<u>" + userId + "</u> \n" + BotMessageTemplates.displayGroupIds(botAuths);
//        } else if ("/showStockNames".equalsIgnoreCase(command)) {
//            return montiService.showStockNames(groupId);
//        } else if (command.toLowerCase(Locale.ROOT).startsWith("/buyRangeFor".toLowerCase(Locale.ROOT))) {
//            String[] split = command.split(" ");
//            if (split.length != 2) {
//                return "<b>please pass the input with predefined format with command space message </b>";
//            }
//            return montiService.buyRangeSpecificStockName(groupId, split[1]);
//        } else if (command.toLowerCase(Locale.ROOT).startsWith("/buyRangeLike".toLowerCase(Locale.ROOT))) {
//            String[] split = command.split(" ");
//            if (split.length != 2) {
//                return "<b>please pass the input with predefined format with command space message </b>";
//            }
//            return montiService.buyRangeStockNameStartsWith(groupId, split[1]);
//        } else if (command.equalsIgnoreCase("/buyRangeAll")) {
//            return montiService.buyRangeForAllStocks(groupId);
//        } else if (command.toLowerCase(Locale.ROOT).startsWith("/addBuyRange".toLowerCase(Locale.ROOT))) {
//            String[] split = command.split(" ");
//            if (split.length != 2) {
//                return "<b>please pass the input with predefined format with command space message </b>";
//            }
//            String[] data = split[1].split(",");
//            if (data.length != 4) {
//                return "The Input should be stockName,category(NSE or BSE or NABSE),buyStart,BuyEnd";
//            }
//            //stockName,category,buyStart,buyEnd
//            if (montiAuth.checkValidUser(userId, groupId))
//                return montiService.addBuyRangeSuggestion(groupId, data[0], data[1], data[2], data[3]);
//            else return "<b> UnAuthorized to add the data </b>";
//        } else if (command.toLowerCase(Locale.ROOT).startsWith("/updateBuyRange".toLowerCase(Locale.ROOT))) {
//            String[] split = command.split(" ");
//            if (split.length != 2) {
//                return "<b>please pass the input with predefined format with command space message </b>";
//            }
//            String[] data = split[1].split(",");
//            if (data.length != 4) {
//                return "The Input should be command space stockName,category(NSE or BSE or NABSE),buyStart,BuyEnd";
//            }
//            //stockName,category,buyStart,buyEnd
//            if (montiAuth.checkValidUser(userId, groupId))
//                return montiService.updateBuyRangeSuggestion(groupId, data[0], data[1], data[2], data[3]);
//            else return "<b> UnAuthorized to update the data </b>";
//
//        } else if (command.toLowerCase(Locale.ROOT).startsWith("/deleteBuyRange".toLowerCase(Locale.ROOT))) {
//            String[] split = command.split(" ");
//            if (split.length != 2) {
//                return "<b>The Input should be \"command space stockName\"</b>";
//            }
//            if (montiAuth.checkValidUser(userId, groupId))
//                return montiService.deleteBuyRangeSuggestion(groupId, split[1]);
//            else return "<b> UnAuthorized to delete the data </b>";
//
//        } else if (command.toLowerCase(Locale.ROOT).startsWith("/migrateFrom".toLowerCase(Locale.ROOT))) {
//            String[] split = command.split(" ");
//            if (split.length != 2) {
//                return "<b>The Input should be \"command space oldGroupName\"</b>";
//            }
//            if (montiAuth.checkValidUser(userId, split[1]) && montiAuth.checkValidUser(userId, groupId))
//                return montiService.updateGroupIdToMigrateData(groupId, split[1]);
//            else return "<b> UnAuthorized to delete the data </b>";
//
//        } else if ("/addHelp".equalsIgnoreCase(command)) {
//            return "<b> addHelp </b>";
//        } else if ("/deleteHelp".equalsIgnoreCase(command)) {
//            return "<b> deleteHelp </b>";
//        } else if ("/updateHelp".equalsIgnoreCase(command)) {
//            return "<b> updateHelp </b>";
//        } else {
//            return BotMessageTemplates.displayHelp();
//        }
//    }
//
//}
