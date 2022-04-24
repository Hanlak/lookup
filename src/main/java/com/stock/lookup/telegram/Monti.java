package com.stock.lookup.telegram;

import com.stock.lookup.util.BotMessageTemplates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Locale;

@Component
public class Monti extends TelegramLongPollingBot {

    @Autowired
    MontiService montiService;

    @Override
    public String getBotUsername() {
        return "lookup_monti_bot";
    }

    @Override
    public String getBotToken() {
        return "1822661060:AAHUUnMIOK4NCLEfKtkexEcwVcwEvJcv9Us";
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        message.setParseMode(ParseMode.HTML);
        message.enableHtml(true);
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText(responseForUpdate(update));
        try {
            executeAsync(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String responseForUpdate(Update update) {
        String command = update.getMessage().getText();
        Message input = update.getMessage();
        User telegramUser = update.getMessage().getFrom();

        Long userId = telegramUser.getId();
        Long chatId = input.getChatId();
        System.out.println("userId:" + userId);
        System.out.println("Command::" + command);
        System.out.println("message::" + input);
        System.out.println("chatId::" + chatId);
        //chatId is groupId;
        //Show Command
        String groupId = "BYG"; //TODO:: chatId.toString();
        if ("/showStockNames".equalsIgnoreCase(command)) {
            return montiService.showStockNames(groupId);
        } else if (command.startsWith("/buyRangeFor")) {
            String[] split = command.split(" ");
            if (split.length != 2) {
                return "<b>please pass the input with predefined format with command <space> message </b>";
            }
            return montiService.buyRangeSpecificStockName(groupId, split[1]);
        } else if (command.toLowerCase(Locale.ROOT).startsWith("/buyRangeLike".toLowerCase(Locale.ROOT))) {
            String[] split = command.split(" ");
            if (split.length != 2) {
                return "<b>please pass the input with predefined format with command <space> message </b>";
            }
            return montiService.buyRangeStockNameStartsWith(groupId, split[1]);
        } else if (command.equalsIgnoreCase("/buyRangeAll")) {
            return montiService.buyRangeForAllStocks(groupId);
        } else
            return BotMessageTemplates.displayHelp();
    }

}
