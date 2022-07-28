import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Bot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "MenskWeatherBot";
    }

    @Override
    public String getBotToken() {
        return "5337224848:AAENjwJ0DviMe6RkdHw1wgF6zh0uH5SCX-4";
    }



    @Override
    public void onUpdateReceived(Update update) {
        try {
            List<City> list = init();

            String message = update.getMessage().getText();

            City city = null;
            for (City c: list){
                if (c.getName().equals(message)){
                    city = c;
                    break;
                }
            }

            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat="+city.getLat()+"&lon="+city.getLon()+"&appid=8214b5994813fe7379c043e827584008");

            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            String str = bufferedReader.readLine();

            JSONObject jsonObject = new JSONObject(str);

            JSONObject weather = (JSONObject) jsonObject.get("main");

            String temp = String.valueOf(weather.getBigDecimal("temp"));

            double t = Double.parseDouble(temp);

            t -= 273;

            t = Math.round(t);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            KeyboardRow keyboardRow = new KeyboardRow();
             keyboardRow.add(new KeyboardButton("Minsk"));
             keyboardRow.add( new KeyboardButton("Brest"));
             keyboardRow.add(new KeyboardButton("Hrodna"));
            keyboardRow.add(new KeyboardButton("Homel"));
            keyboardRow.add(new KeyboardButton("Vitebsk"));
             keyboardRow.add(new KeyboardButton("Mogilev"));

            //            //создаем клавиатуру и устанавливаем в нее наш ряд клавиш
             ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
             replyKeyboardMarkup.setKeyboard
                     (Collections.singletonList(keyboardRow));
            //устанавливаем в наш объект лавиатуру
             sendMessage.setReplyMarkup(replyKeyboardMarkup);

            sendMessage.setText("The weather in city "+ city.getName()+" "+String.valueOf(t)+"градусов");

            execute(sendMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<City> init(){
        List<City> list = new ArrayList<>();
        City city = new City("Minsk","53.893009", "27.567444");
        City city2 = new City("Vitebsk", "55.1904", "30.2049");
        City city3 = new City("Hrodna", "53.6884", "23.8258");
        City city1 = new City("Mogilev", "53.8981", "30.3325");
        City city4 = new City("Brest", "52.0975", "23.6877");
        City city5 = new City("Homel", "52.4345", "30.9754");

        list.add(city);
        list.add(city1);
        list.add(city2);
        list.add(city3);
        list.add(city4);
        list.add(city5);

        return list;
    }
}











