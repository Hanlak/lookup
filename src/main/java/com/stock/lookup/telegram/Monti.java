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
//        System.out.println("command:" + command);
//        System.out.println("userId::" + userId);
//        System.out.println("groupTitle::" + name);
//        System.out.println("groupId::" + groupId);
//        if (ObjectUtils.isEmpty(command) || ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(groupId)) {
//            return "<b>Monti is Having Trouble To Process your Request..:(</b>";
//        }
//
//        if ("/purge".equalsIgnoreCase(command)) {
//            if (userId.equalsIgnoreCase("1684141700")) {
//                return montiAuth.purge(groupId);
//            }
//            return "<b> Access for Bot Creator Only </b>";
//        }
//
//        /**
//         * UNREGISTER TO REMOVE ACCESS OF ADD,UPDATE,DELETE AND OTHER WRITE ACCESS
//         */
//        if ("/unRegister".equalsIgnoreCase(command)) {
//            if (montiAuth.checkValidUser(userId, groupId)) {
//                return montiAuth.unRegister(groupId, userId, name);
//                //TODO:: might have to delete the data on the groupId
//            } else return "<b> You Haven`t Registered :) </b>";
//        }
//        /**
//         * REGISTER TO GET ACCESS OF ADD,UPDATE,DELETE AND OTHER WRITE ACCESS
//         */
//        else if ("/register".equalsIgnoreCase(command)) {
//            boolean register = montiAuth.registerUser(groupId, userId, name);
//            return register ? "<b>You Are Registered As ADMIN :) </b>" : "<b>Failed to Register As Admin. There Might be an Existing Admin</b>";
//        }
//        /**
//         * PASS THE OWNERSHIP TO ANOTHER USER
//         */
//        else if (command.startsWith("/updateUser")) {
//            String[] split = command.split(" ");
//            if (split.length != 2) {
//                return "<b>please pass the input with predefined format with "
//                        + "\n"
//                        + "=> command#message </b>";
//            }
//            if (montiAuth.checkValidUser(userId, groupId)) {
//                boolean updateUser = montiAuth.updateUser(split[1], groupId);
//                return updateUser ? "<b>New User given Admin Access:)</b>" : "<b>Failed to Give Admin Access</b>";
//            } else {
//                return "<b> UnAuthorized to Change the Access </b>";
//            }
//        }
//
//        /**
//         * GET THE GROUP_ID`S OF THE REQUESTED USER
//         */
//        else if ("/getGroupId".equalsIgnoreCase(command)) {
//            List<BotAuth> botAuths = montiAuth.getGroupIds(userId);
//            return "<u>" + userId + "</u> \n"
//                    + BotMessageTemplates.displayGroupIds(botAuths);
//        } else if ("/showStockNames".equalsIgnoreCase(command)) {
//            return montiService.showStockNames(groupId);
//        }
//        /**
//         * SHOW::
//         * BUY RANGE SUGGESTION::PRECISE
//         */
//        else if (command.toLowerCase(Locale.ROOT).startsWith("/buyRangeFor".toLowerCase(Locale.ROOT))) {
//            String[] split = command.split("#");
//            if (split.length != 2) {
//                return "<b>please pass the input with predefined format with::"
//                        + "\n"
//                        + "=>command#message </b>";
//            }
//            return montiService.buyRangeSpecificStockName(groupId, split[1].trim());
//        }
//        /**
//         * SHOW::
//         * BUY RANGE SUGGESTION::STARTS WITH
//         */
//        else if (command.toLowerCase(Locale.ROOT).startsWith("/buyRangeLike".toLowerCase(Locale.ROOT))) {
//            String[] split = command.split("#");
//            if (split.length != 2) {
//                return "<b>please pass the input with predefined format with::"
//                        + "\n"
//                        + "=>command#message </b>";
//            }
//            return montiService.buyRangeStockNameStartsWith(groupId, split[1].trim());
//        } else if (command.equalsIgnoreCase("/buyRangeAll")) {
//            return montiService.buyRangeForAllStocks(groupId);
//        }
//        /**
//         * ADD SUGGESTION
//         */
//
//        else if (command.toLowerCase(Locale.ROOT).startsWith("/addBuyRange".toLowerCase(Locale.ROOT))) {
//            String[] split = command.split("#");
//            if (split.length != 2) {
//                return "<b>please pass the input with predefined format with::"
//                        + "\n"
//                        + "=>command#stockName,category(NSE or BSE or NABSE),buyStart,BuyEnd </b>";
//            }
//            String[] data = split[1].split(",");
//            System.out.println("Data::" + data.length + ":::" + data);
//            if (data.length != 4) {
//                return "The Input should be stockName,category(NSE or BSE or NABSE),buyStart,BuyEnd";
//            }
//            //stockName,category,buyStart,buyEnd
//            if (montiAuth.checkValidUser(userId, groupId))
//                return montiService.addBuyRangeSuggestion(groupId, data[0].trim().toUpperCase(Locale.ROOT), data[1].trim().toUpperCase(Locale.ROOT), data[2].trim(), data[3].trim());
//            else return "<b> UnAuthorized to add the data </b>";
//        }
//
//        /**
//         * UPDATE SUGGESTION
//         */
//        else if (command.toLowerCase(Locale.ROOT).startsWith("/updateBuyRange".toLowerCase(Locale.ROOT))) {
//            String[] split = command.split("#");
//            if (split.length != 2) {
//                return "<b>please pass the input with predefined format with::"
//                        + "\n"
//                        + "=>command#stockName,updatedCategory(NSE or BSE or NABSE),updatedBuyStart,updatedBuyEnd </b>";
//            }
//            String[] data = split[1].split(",");
//            if (data.length != 4) {
//                return "The Input should be command#stockName,category(NSE or BSE or NABSE),buyStart,BuyEnd";
//            }
//            //stockName,category,buyStart,buyEnd
//            if (montiAuth.checkValidUser(userId, groupId))
//                return montiService.updateBuyRangeSuggestion(groupId, data[0].trim().toUpperCase(Locale.ROOT), data[1].trim().toUpperCase(Locale.ROOT), data[2].trim(), data[3].trim());
//            else return "<b> UnAuthorized to update the data </b>";
//
//        }
//        /**
//         * DELETE SUGGESTION
//         */
//        else if (command.toLowerCase(Locale.ROOT).startsWith("/deleteBuyRange".toLowerCase(Locale.ROOT))) {
//            String[] split = command.split("#");
//            if (split.length != 2) {
//                return "<b>The Input should be \"command#stockName\"</b>";
//            }
//            if (montiAuth.checkValidUser(userId, groupId))
//                return montiService.deleteBuyRangeSuggestion(groupId, split[1].trim().toUpperCase(Locale.ROOT));
//            else return "<b> UnAuthorized to delete the data </b>";
//
//        }
//
//        /**
//         * MIGRATE SUGGESTION DATA FROM OLD GROUP TO EXISTING GROUP
//         */
//
//        else if (command.toLowerCase(Locale.ROOT).startsWith("/migrateFrom".toLowerCase(Locale.ROOT))) {
//            String[] split = command.split("#");
//            if (split.length != 2) {
//                return "<b>The Input should be \n => \"command#oldGroupName\"</b>";
//            }
//            if (montiAuth.checkValidUser(userId, split[1].trim()) && montiAuth.checkValidUser(userId, groupId))
//                return montiService.updateGroupIdToMigrateData(groupId, split[1].trim());
//            else return "<b> UnAuthorized to delete the data </b>";
//
//        }
//        /**
//         * HELP INFO
//         */
//        else if ("/addHelp".equalsIgnoreCase(command)) {
//            return BotMessageTemplates.displayAddHelp();
//        } else if ("/deleteHelp".equalsIgnoreCase(command)) {
//            return BotMessageTemplates.displayDeleteHelp();
//        } else if ("/updateHelp".equalsIgnoreCase(command)) {
//            return BotMessageTemplates.displayUpdateHelp();
//        } else if ("/userHelp".equalsIgnoreCase(command)) {
//            return BotMessageTemplates.displayUserHelp();
//        } else if ("/showHelp".equalsIgnoreCase(command)) {
//            return BotMessageTemplates.displayShowHelp();
//        } else {
//            return BotMessageTemplates.botHelp();
//        }
//    }
//}
//
