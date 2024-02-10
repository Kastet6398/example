import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
   private static final Logger log = LoggerFactory.getLogger(Bot.class);
   @Override
   public void onUpdateReceived(Update update) {
      if (update.hasMessage() && update.getMessage().hasText()) {
         String textFromUser = update.getMessage().getText();
         Long userId = update.getMessage().getChatId();
         String userFirstName = update.getMessage().getFrom().getFirstName();
         log.info("[{}, {}] : {}", userId, userFirstName, textFromUser);

         try {
            if (textFromUser.equals("/weather")) {
               this.sendApiMethod(hermitageInlineKeyboardAb(userId));
            }
         } catch (TelegramApiException e){
            log.error(String.valueOf(e));
         }
      } else if (update.hasCallbackQuery()) {
         CallbackQuery callbackQuery = update.getCallbackQuery();
         String data = callbackQuery.getData();
         Long userId = update.getCallbackQuery().getMessage().getChatId();

         try {
            if (data.equals("/dnipro")) {
               this.sendApiMethod(getWeatherInCity(userId, "днепр"));
            }
         } catch (TelegramApiException e){
            log.error(String.valueOf(e));
         }

      }else {
         log.warn("Unexpected update from user");
      }
   }
   public static SendMessage getWeatherInCity(long chat_id, String city){
      SendMessage message = new SendMessage();
      message.setChatId(chat_id);
      message.setText(Utils.getWeather(city));
      return message;
   }

   public static SendMessage hermitageInlineKeyboardAb (long chat_id){

      SendMessage message = new SendMessage();
      message.setChatId(chat_id);
      message.setText("Выберите город, где вы хотите узнать погоду");

      InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

      List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
      List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

      InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
      InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
      InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();

      inlineKeyboardButton1.setText("Днепр");
      inlineKeyboardButton1.setCallbackData("/dnipro");

      inlineKeyboardButton2.setText("Харьков");
      inlineKeyboardButton2.setCallbackData("/kharkiv");

      inlineKeyboardButton3.setText("Полтава");
      inlineKeyboardButton3.setCallbackData("/poltava");

      rowInline1.add(inlineKeyboardButton1);
      rowInline1.add(inlineKeyboardButton2);
      rowInline1.add(inlineKeyboardButton3);

      rowsInline.add(rowInline1);

      markupInline.setKeyboard(rowsInline);
      message.setReplyMarkup(markupInline);

      return message;
   }

   @Override
   public String getBotUsername() {
      return "smth13_qBot";
   }

   @Override
   public String getBotToken() {
      return "6354009793:AAErEY6AKF0crpEfNRtTGDOUiEnnG_xgaUg";
   }
}
