import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    // 7b01b298bf35aa5cb8ec786809f26953
    // http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID={7b01b298bf35aa5cb8ec786809f26953}

    public static String getWeather(String message, Model model) throws IOException {
        String result = "";
        String url_raw = "http://api.openweathermap.org/data/2.5/weather?q=" + message +"&units=metric&APPID=7b01b298bf35aa5cb8ec786809f26953";
        URL url = new URL(url_raw);
        System.out.println(message);

        Scanner in = new Scanner((InputStream)url.getContent());
        while(in.hasNext()) {
            result += in.nextLine();
        }
        JSONObject object = new JSONObject(result);

        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");

        model.setTemp(main.getDouble("temp"));

        model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");

        for (int i = 0; i < getArray.length(); i++) {

            JSONObject obj = getArray.getJSONObject(i);

            model.setIcon((String) obj.get("icon"));

            model.setMain((String) obj.get("main"));

        }

        return "Город: " + message + "\n" +

                "Температура: " + model.getTemp() + "C" + "\n" +

                "Влажность:" + model.getHumidity() + "%" + "\n" +

                "Небо: " + model.getMain() + "\n" +

                "http://openweathermap.org/img/w/" + model.getIcon() + ".png";
    }
}
