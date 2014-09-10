package Auth;

import API.URLReader;
import java.util.TreeMap;
import org.json.JSONObject;

public class client {
    
    private static String user(String path) throws Exception {
        return auth.request("/v1/user"+path+"?");
    }
    
    private static String futures(String coin, String path, String params) throws Exception {
        if (params != null) { params = "?"+params; }
        if (coin.equals("btc")) { return auth.request("/v1/weeklyfutures"+path+params); }
        if (coin.equals("ltc")) { return auth.request("/v1/ltcfutures"+path+params); }
        else { return null; }
    }
    
    public static String get_info() throws Exception {
        return user("/get_info");
    }
    
    public static String get_balance() throws Exception {
        return user("/get_balance");
    }
    
    public static Double balance(String coin) throws Exception {
        if (coin.equals("btc")) { return URLReader.jsonConverter(get_balance()).getJSONObject("futures_wallet").getDouble("btc"); }
        if (coin.equals("ltc")) { return URLReader.jsonConverter(get_balance()).getJSONObject("futures_wallet").getDouble("btc"); }
        else { return null; }
    }
    
    public static String del_token() throws Exception {
        return user("/delete_token");
    }
    
    public static String orders(String coin) throws Exception {
        String res = futures(coin, "/orders", "");
        if (res.equals("[]")) { return "no orders"; }
        else { return res; }
    }
    
    public static String records(String coin) throws Exception {
        return futures(coin, "/records", "");
    }
    
    private static TreeMap<String, String> makeMap(String str, JSONObject json) {
        TreeMap<String, String> newMap = new TreeMap<>();
        TreeMap<String, String> map = new TreeMap<>();
        if ( json != null ) {
            for (Object key : json.keySet()) {
                JSONObject obj = json.getJSONObject((String) key);
                map.put("times", (String) key);
                map.put("total", obj.getString("total"));
                map.put("price", obj.getString("avg_price"));
                map.put("status", obj.getString("ykl"));
                newMap.put(str, map.toString());
            }
        } return newMap;
    }
    
    public static TreeMap<String, String> position(String coin) throws Exception {
        TreeMap<String, String> map = new TreeMap<>();
        String res = futures(coin, "/position", "");
        if (res.equals("[]")) { return null; }
        else { 
            try { 
                JSONObject jsonSell, jsonBuy;
                try { jsonSell = URLReader.jsonConverter(res).getJSONObject("sell"); }
                catch (Exception e) { jsonSell = null; }
                try { jsonBuy = URLReader.jsonConverter(res).getJSONObject("buy"); }
                catch (Exception e) { jsonBuy = null; }
                map.putAll(makeMap("sell", jsonSell));
                map.putAll(makeMap("buy", jsonBuy));
            }
            catch (Exception e) { }
        }
        return map;
    }
    
    public static Double[] sellPosition(String coin) throws Exception {
        TreeMap<String, String> map = position("btc");
        Double[] hold = {0., 0.};
        try {String text = map.get("sell").replaceAll("=", ":");
        Double amount = URLReader.jsonConverter(text).getDouble("total");
        Double times = URLReader.jsonConverter(text).getDouble("times");
        hold[0] = amount; hold[1] = times;} catch (Exception e) {}
        return hold;
    }
    
    public static Double[] buyPosition(String coin) throws Exception {
        TreeMap<String, String> map = position("btc");
        Double[] hold = {0., 0.};
        try {String text = map.get("buy").replaceAll("=", ":");
        Double amount = URLReader.jsonConverter(text).getDouble("total");
        Double times = URLReader.jsonConverter(text).getDouble("times");
        hold[0] = amount; hold[1] = times;} catch (Exception e) {}
        return hold;
    }
    
    public static String openBuy(String coin, Double vol, Double price, Integer times) throws Exception {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("buy_num", vol.toString());
        params.put("buy_price", price.toString());
        params.put("times", times.toString());
        return futures(coin, "/open_buy", auth.encodeParams(params));
    }
    
    public static String closeBuy(String coin, Double vol, Double price, Integer times) throws Exception {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("amount", vol.toString());
        params.put("price", price.toString());
        params.put("times", times.toString());
        return futures(coin, "/close_buy", auth.encodeParams(params));
    }
    
    public static String openSell(String coin, Double vol, Double price, Integer times) throws Exception {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("sell_num", vol.toString());
        params.put("sell_price", price.toString());
        params.put("times", times.toString());
        return futures(coin, "/open_sell", auth.encodeParams(params));
    }
    
    public static String closeSell(String coin, Double vol, Double price, Integer times) throws Exception {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("amount", vol.toString());
        params.put("price", price.toString());
        params.put("times", times.toString());
        return futures(coin, "/close_sell", auth.encodeParams(params));
    }
    
    public static String cancel(String coin, String bs, Long num) throws Exception {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("bs", bs);
        params.put("no", num.toString());
        return futures(coin, "/cancel_order", auth.encodeParams(params));
    }
    
    public static String cancelAll(String coin, String bs) throws Exception {
        // bs = buy, sell, all
        TreeMap<String, String> params = new TreeMap<>();
        params.put("bs", bs);
        return futures(coin, "/cancel_all", auth.encodeParams(params));
    }
}
