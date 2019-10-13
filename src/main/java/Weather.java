import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    // "&units=metric&APPID=7b01b298bf35aa5cb8ec786809f26953"
    // "http://api.openweathermap.org/data/2.5/weather?q="
    private  String Url;
    private   String Token;

    public String getUrl() { return  Url; }
    public String getToken() { return  Token; }

    public  Weather(String apiURL, String apiToken){
        this.Url = apiURL;
        this.Token = apiToken;
    }

    private static String getApiURL(String url,String message, String token){
        StringBuilder url_raw = new StringBuilder(url);
        url_raw.append(message);
        url_raw.append(token);
        return  url_raw.toString();
    }

    public  String getWeather(String message, Model model) throws IOException {
        StringBuilder result = new StringBuilder();
        String url_raw = getApiURL(Url,message, Token);
        URL url = new URL(url_raw);

        Scanner in = new Scanner((InputStream)url.getContent());
        while(in.hasNext()) {
            result.append(in.nextLine());
        }
        JSONObject object = new JSONObject(result.toString());
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
