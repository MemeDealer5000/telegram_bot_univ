import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {

    private static String PROXY_HOST = "127.0.0.1" /* proxy host */;
    private static Integer PROXY_PORT = 9150 /* proxy port */;
    private static  String BOT_TOKEN = "962457052:AAEvBUAG1YAy3UX8b4famZNUfn0BpP75LHs";
    private  static  String BOT_NAME = "weather_report_test_bot";
    private long chat_id;

    protected Bot(DefaultBotOptions botOptions) {
        super(botOptions);
    }

    public void sendMsg(Message message, String text){
        SendMessage sentMessage = new SendMessage();
        sentMessage.enableMarkdown(true);
        sentMessage.setChatId(message.getChatId());
        //sentMessage.setReplyToMessageId((message.getMessageId()));
        sentMessage.setText(text);
        try{
            setKeyboardButtons(sentMessage);
            execute(sentMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }

    }

    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        chat_id = message.getChatId();
        if (message != null && message.hasText()) {
            switch (message.getText()){
                case "/start":
                    sendMsg(message, "Ну че народ,погнали,нахуй?");
                    break;
                case "/help":
                    sendMsg(message, "Напиши Привет,чтоб увидеть приветствие \nНапиши название города, чтоб увидеть погоду");
                    break;
                case "/settings":
                    sendMsg(message,"What should we set?");
                    break;
                case "Привет":
                    sendMsg(message,"И тебе привет,человек)");
                    break;
                default:
                    try{
                        sendMsg(message, Weather.getWeather(message.getText(), model));
                    } catch (IOException e) {
                        sendMsg(message, "Такого города нет");
                    }
            }
        }
    }

    public void setKeyboardButtons(SendMessage message) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        message.setReplyMarkup(keyboard);
        keyboard.setSelective(true);
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRow = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/settings"));

        keyboardRow.add(keyboardFirstRow);
        keyboard.setKeyboard(keyboardRow);
    }

    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }
}