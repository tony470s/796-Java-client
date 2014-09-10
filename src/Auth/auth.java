package Auth;

import java.net.URLEncoder;
import java.util.*;
import API.URLReader;

public class auth {
    
    private static final String apiKey = "";
    private static final String secretKey = "";
    private static final String appid = "";
    
    public static String encodeParams(TreeMap<String, String> params) {
        String data = "";
        for (String key : params.keySet()) {
            data += URLEncoder.encode(key)+"="+URLEncoder.encode(params.get(key))+"&";
        }
            data = data.substring(0, data.length()-1);
            return data;
    }
    
    private static String params() throws Exception {
        String data;
        String timestamp = Long.toString(System.currentTimeMillis() / 1000L);
        
        TreeMap<String, String> params = new TreeMap<>();
		params.put("appid", appid);
		params.put("apikey", apiKey);
		params.put("secretkey", secretKey);
		params.put("timestamp", timestamp);
		return encodeParams(params);
    }
    
    private static String accessToken() throws Exception {
		String timestamp = Long.toString(System.currentTimeMillis() / 1000L);
		String data = params();
		
		String sig = HmacSignature.HMAC(data, secretKey);
		sig = Base64.getUrlEncoder().encodeToString(sig.getBytes());
		
		String tokenURL = "https://796.com/oauth/token?" + data + "&sig=" + sig;
                String token = URLReader.json(tokenURL).getJSONObject("data").getString("access_token");
                return token;
    }
    
    public static String request(String path) throws Exception {
        String token = accessToken();
        return URLReader.json("https://796.com"+path+"access_token="+token).get("data").toString();
    }
}