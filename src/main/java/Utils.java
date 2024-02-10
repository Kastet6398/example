import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Utils {
   public static String getWeather(String city) {

      try {
         Document doc = Jsoup.connect(STR."https://sinoptik.ua/погода-\{city}-303007131").get();

         return doc.select(".today-temp").text();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
