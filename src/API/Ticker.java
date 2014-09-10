package API;
import org.json.*;
import java.util.ArrayList;

public class Ticker {

    private static final String base = "http://api.796.com/v3/futures/";
    private static final String[] htmls = {"ticker.html", "depth.html", "trades.html"};
    private static final String[] types = {"?type=weekly", "?type=ltc"};

    private static String requestPath(String str, Integer x) {
        String request = "";
    	if (str.equals("btc")) { request = base+htmls[x]+types[0]; }
		if (str.equals("ltc")) { request = base+htmls[x]+types[1]; }
		return request;
    }

    private static String requestPath(String str) {
    	String request = "";
    	if (str.equals("btc")) { request = "http://api.bravenewcoin.com/ticker/bnc_ticker_btc_data.json"; }
		if (str.equals("ltc")) { request = "http://api.bravenewcoin.com/ticker/bnc_ticker_ltc_data.json"; }
		return request;
    }

    public static JSONObject ticker(String str) throws Exception {
    	return URLReader.json(requestPath(str, 0)).getJSONObject("ticker");
    }
    
    public static JSONObject bnc_ticker(String str) throws Exception {
    	return URLReader.json(requestPath(str));
    }

    public static Double last(String str) throws Exception {
    	return ticker(str).getDouble("last");
    }

    public static Double bnc_last(String str) throws Exception {
    	return bnc_ticker(str).getDouble("last_price");
    }

    public static ArrayList<Double> bnc_data(String str) throws Exception {
    	ArrayList<Double> list = new ArrayList<>();
    	JSONArray data = bnc_ticker(str).getJSONArray("ticker_data");

        if (data != null) { 
        	int len = data.length();
            for (int i = 0; i < len; i++){ list.add(data.getDouble(i)); }
        } return list;
    }
}