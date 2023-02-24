import org.glassfish.jersey.jaxb.internal.XmlCollectionJaxbProvider;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Properties;


class Bot extends TelegramLongPollingBot {
    @Override
    // TODO додати перевірку на null (.NullPointerException) т.к у гіф-анімацій немає імені яке йде за стандартом null
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasAnimation()) {
            Animation gif = update.getMessage().getAnimation();
            Long chatId = update.getMessage().getChatId();

            SendAnimation sendAnimation = new SendAnimation();
            sendAnimation.setChatId(chatId.toString());
            sendAnimation.setAnimation(new InputFile(gif.getFileId()));

            try {
                execute(sendAnimation);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);

    }

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e);


        }
    }

    @Override
    public String getBotUsername() {
        return "ТестовийБотВНТУ";
    }

    @Override
    public String getBotToken() {
        Properties prop = new Properties();
        try {
            //load a properties file from class path, inside static method
            prop.load(XmlCollectionJaxbProvider.App.class.getClassLoader().getResourceAsStream("config.properties"));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop.getProperty("token");
    }


}
