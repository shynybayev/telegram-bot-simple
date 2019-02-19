import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.*;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {
    private long chatId;
    private String lastMessage;
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    Top top  = new Top();

    public void onUpdateReceived(Update update) { //Реагирует на входящие сообщения
        update.getUpdateId();
        SendMessage sendMessage =
                new SendMessage().setChatId(update.getMessage().getChatId());
        chatId = update.getMessage().getChatId();

        String text = update.getMessage().getText();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        try{
            sendMessage.setText(getMessage(text));
            execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }

    }

    public String getMessage(String msg){
        ArrayList<KeyboardRow> keyboard = new ArrayList();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        if (msg.contains("Привет") || msg.contains("Меню")){
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("Популярное");
            keyboardFirstRow.add("Новости\uD83D\uDCF0");
            keyboardSecondRow.add("Полезная информация");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Выбрать";
        }
        if (msg.contains("Полезная информация")){
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("Информация о книге");
            keyboardFirstRow.add("Автор");
            keyboardSecondRow.add("Меню");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Выбрать";
        }
        if (msg.contains("Популярное")){
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("Стихи");
            keyboardFirstRow.add("Авторы");
            keyboardSecondRow.add("Меню");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Выбрать";
        }
        if (msg.contains("/person")){
            msg = msg.replace("/person ","");
            return getInfoPerson(msg);
        }
        if (msg.equals("Стихи") || msg.equals("Книги\uD83D\uDCDA")) {
            lastMessage = msg;
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("Сегодня");
            keyboardFirstRow.add("За месяц");
            keyboardFirstRow.add("За все время");
            keyboardFirstRow.add("За неделю");
            keyboardFirstRow.add("Меню");
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Выбрать...";
        }
        if (lastMessage.equals("Книги\uD83D\uDCDA")){
            if (msg.equals("Сегодня")){
                return getInfoBook(top.getTopBooks(msg));
            }
            if (msg.equals("За месяц")){
                return getInfoBook(top.getTopBooks(msg));
            }
            if (msg.equals("За все время")){
                return getInfoBook(top.getTopBooks(msg));
            }
            if (msg.equals("За неделю")){
                return getInfoBook(top.getTopBooks(msg));
            }
        } else if (lastMessage.equals("Стихи")){
            if (msg.equals("Сегодня")){
                return getTopPoem(top.getTopPoems(msg));
            }
            if (msg.equals("За месяц")){
                return getTopPoem(top.getTopPoems(msg));
            }
            if (msg.equals("За все время")){
                return getTopPoem(top.getTopPoems(msg));
            }
            if (msg.equals("За неделю")){
                return getTopPoem(top.getTopPoems(msg));
            }

        }

        return "Не понял";
    }

    public String getBotUsername() {
        return "@TestPetprojectBot";
    }

    public String getBotToken() {
        return "739444862:AAFMsy1tcpUUX-ygD7EN58_uKFITRfidJqA";
    }

//    public String input(String msg) {
//        if (msg.contains("Cәлем") || msg.contains("Hi") || msg.contains("Hello") || msg.contains("Привет")){
//           return "Привет, дружище";
//        }
//        if (msg.contains("Информация о книге")){
//            return getInfoBook();
//        }
//        if (msg.contains("/person")){
//            msg = msg.replace("/person ","");
//            return getInfoPerson(msg);
//        }
//        return "Не понял";
//    }

//    public String getInfoBook(String[] href){
//        SendPhoto sendPhotoRequest = new SendPhoto();
//
//        try(InputStream in = new URL(book.getImg()).openStream()) {
//            Files.copy(in, Paths.get("D:\\srgBook"));
//            sendPhotoRequest.setChatId(chatId);
//            sendPhotoRequest.setPhoto(new File("D:\\srgBook"));
//            execute(sendPhotoRequest);
//            Files.delete(Paths.get("D:\\srgBook"));
//        }
//        catch(IOException ex){
//            System.out.println("File not found");
//        }
//        catch (TelegramApiException e){
//            e.printStackTrace();
//        }
//
//        String info = book.getTitle()
//                + "\nAвтор: " + book.getAuthorName()
//                + "\nЖанр: " + book.getGenres()
//                + "\n\nКоличество лайков: " + book.getLikes()
//                + "\n\nПоследние комментарии: " + book.getCommentList();
//        return info;
//    }

    public String getInfoBook(String[] href) {
        String info = "";
        for (int i = 0; i < href.length; i++) {
            info = "";
            Book book = new Book(href[i]);
            if (Files.exists(Paths.get("D:\\srgBook"))) {
                try {
                    Files.delete(Paths.get("D:\\srgBook"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            SendPhoto sendPhotoRequest = new SendPhoto();
            try (InputStream in = new URL(book.getImg()).openStream()) {
                Files.copy(in, Paths.get("D:\\srgBook"));
                sendPhotoRequest.setChatId(chatId);
                sendPhotoRequest.setPhoto(new File("D:\\srgBook"));
                execute(sendPhotoRequest);
                if (Files.exists(Paths.get("D:\\srgBook"))){
                    Files.delete(Paths.get("D:\\srgBook"));
                }
            } catch (IOException ex) {
                System.out.println("File not found");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            info = book.getTitle()
                    + "\nАвтор " + book.getAuthorName()
                    + "\nЖанр " + book.getGenres()
                    + "\n\nОписание\n" + book.getDescription()
                    + "\n\nКоличество лайков " + book.getLikes()
                    + "\n\nПоследние комментарии\n" + book.getCommentList();
            SendMessage sendMessage = new SendMessage().setChatId(chatId);
            try {
                sendMessage.setText(info);
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return "\uD83D\uDC40";
    }

    public String getInfoPerson(String msg){
        Author author = new Author(msg);

        SendPhoto sendPhotoRequest = new SendPhoto();
        try(InputStream inputStream = new URL(author.getImg()).openStream()){
            Files.copy(inputStream, Paths.get("D:\\srgBook"));
            sendPhotoRequest.setChatId(chatId);
            sendPhotoRequest.setPhoto(new File("D:\\srgBook"));
            execute(sendPhotoRequest);
            Files.delete(Paths.get("D:\\srgBook"));
        } catch (IOException e) {
            System.out.println("File not found");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return author.getInfoPerson();
    }

    public String getTopPoem(String[] text) {
        SendMessage sendMessage = new SendMessage().setChatId(chatId);
        for (int i = 0; i < text.length; i++) {
            sendMessage.setText(text[i]);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return "\uD83D\uDC40"; //эмоджи смайлик
    }
}
