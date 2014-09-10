package Trader;

import API.Ticker;
import Auth.client;

public class Trade {

    private static final double ratio = 0.25;
    
    public static void Buy() throws Exception {
        while ( true ) {
            try {
                Double balance = client.balance("btc");
                Double volume = balance * ratio;
                Double price = Ticker.last("btc");
                client.cancelAll("btc", "buy");
                client.openBuy("btc", volume, price, 10); break;
            } catch (Exception e) {} 
        }
    }
    
    public static void Sell() throws Exception {
        while ( true ) {
            try {
                Double balance = client.balance("btc");
                Double volume = balance * ratio;
                Double price = Ticker.last("btc");
                client.cancelAll("btc", "sell");
                client.openSell("btc", volume, price, 10); break;
            } catch (Exception e) {}
        }
    }
    
    public static void closeBuy() throws Exception {
        while (true) {
            try {
                Double[] hold = client.buyPosition("btc");
                Double price = Ticker.last("btc");
                client.cancelAll("btc", "buy");
                client.closeBuy("btc", hold[0], price, hold[1].intValue()); break;
            } catch (Exception e) {}
        }
    }
    
    public static void closeSell() throws Exception {
        while (true) {
            try {
                Double[] hold = client.sellPosition("btc");
                Double price = Ticker.last("btc");
                client.cancelAll("btc", "buy");
                client.closeSell("btc", hold[0], price, hold[1].intValue()); break;
            } catch (Exception e) {}
        }
    }
}
