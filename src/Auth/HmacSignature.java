package Auth;

import java.util.Formatter;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSignature {
    
    private static final String HMAC_SHA1 = "HmacSHA1";
    
    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) { formatter.format("%02x", b); }
        return formatter.toString();
    }
    
    public static String HMAC(String data, String key, String algo) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), algo);
        Mac mac = Mac.getInstance(algo);
        mac.init(signingKey);
        return toHexString(mac.doFinal(data.getBytes()));
    }
    
    public static String HMAC(String data, String key) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);
        return toHexString(mac.doFinal(data.getBytes()));
    }
}
