import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Timer;

public class Main {

    private static String PROXY_HOST = "162.243.28.56" /* proxy host */;
    private static Integer PROXY_PORT = 25028 /* proxy port */;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyHost(PROXY_HOST);
        botOptions.setProxyPort(PROXY_PORT);
        // Select proxy type: [HTTP|SOCKS4|SOCKS5] (default: NO_PROXY)
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        Bot myBot = new Bot(botOptions);
        Timer timer = new Timer();
        try {
            telegramBotsApi.registerBot(myBot);
            System.out.println("Bot registered");
            timer.schedule(myBot.reminder, 0, 4000);

        } catch (TelegramApiException e){
            System.out.println("Error is here");
            e.printStackTrace();
        }
    }
}
