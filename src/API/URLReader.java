package API;
import java.net.*;
import java.io.*;
import org.json.*;

public class URLReader {

    public static String text(String path) throws Exception {
        // loads text
        URL url = new URL(path);
        try (BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()))) {
            String inputLine;
            String lines = "";
            while ((inputLine = in.readLine()) != null) {
                lines += inputLine;
            } return lines;
        }
    }

    public static JSONObject json(String path) throws Exception {
        // loads json object
        String jsonText = text(path);
        JSONObject json = new JSONObject(jsonText);
        return json;
    }
    
    public static JSONObject jsonConverter(String text) throws Exception {
        return new JSONObject(text);
    }

}