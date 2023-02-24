import org.glassfish.jersey.jaxb.internal.XmlCollectionJaxbProvider;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;
import java.util.Properties;


class Bot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = update.getMessage().getText();

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId.toString());
                sendMessage.setText(text);

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message.hasAnimation()) {
                Animation gif = update.getMessage().getAnimation();

                SendAnimation sendAnimation = new SendAnimation();
                sendAnimation.setChatId(chatId.toString());
                sendAnimation.setAnimation(new InputFile(gif.getFileId()));

                try {
                    execute(sendAnimation);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message.hasPhoto()) {
                List<PhotoSize> photoSizes = message.getPhoto();
                PhotoSize photo = photoSizes.get(photoSizes.size() - 1);

                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId.toString());
                sendPhoto.setPhoto(new InputFile(photo.getFileId()));

                try {
                    execute(sendPhoto);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message.hasDocument()) {
                Document document = message.getDocument();

                SendDocument sendDocument = new SendDocument();
                sendDocument.setChatId(chatId.toString());
                sendDocument.setDocument(new InputFile(document.getFileId()));

                try {
                    execute(sendDocument);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message.hasVoice()) {
                Voice voice = message.getVoice();

                SendVoice sendVoice = new SendVoice();
                sendVoice.setChatId(chatId.toString());
                sendVoice.setVoice(new InputFile(voice.getFileId()));

                try {
                    execute(sendVoice);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message.hasLocation()) {
                Double latitude = message.getLocation().getLatitude();
                Double longitude = message.getLocation().getLongitude();

                if (latitude != null && longitude != null) {
                    SendLocation sendLocation = new SendLocation();
                    sendLocation.setChatId(message.getChatId().toString());
                    sendLocation.setLatitude(latitude);
                    sendLocation.setLongitude(longitude);

                    try {
                        execute(sendLocation);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Якщо геолокація містить порожні значення
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId().toString());
                    sendMessage.setText("Вибачте, але геолокацію не було відправлено.");

                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
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
