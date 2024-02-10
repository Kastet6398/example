import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URLEncoder;

public class Utils {
   public static String getWeather(String city) {

      try {
         Document doc = Jsoup.connect(STR."https://sinoptik.ua/%D0%BF%D0%BE%D0%B3%D0%BE%D0%B4%D0%B0-\{URLEncoder.encode(city, "UTF-8")}").get();

         return doc.select(".today-temp").text();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}