package Trader;

import Auth.client;
import Index.*;
import java.util.ArrayList;

public class AutoClose {
    
    private static Integer sellSignal() {
        
        DataSet dataSet = new DataSet();
        dataSet.generateDataSet("price.csv"); // read data
        
        RSI rsi = new RSI();
        rsi.getRSI(dataSet.getInstances(), 14);
        
        ArrayList<Double> rsiVal = rsi.getRSISet();
        if (rsiVal.get(rsiVal.size()-2) < 70 && 
                70 < rsiVal.get(rsiVal.size()-1)) { return -1; }
        if (rsiVal.get(rsiVal.size()-2) < 30 && 
                30 < rsiVal.get(rsiVal.size()-1)) { return 1; }
        if (rsiVal.get(rsiVal.size()-2) > 70 && 
                70 > rsiVal.get(rsiVal.size()-1)) { return -1; }
        if (rsiVal.get(rsiVal.size()-2) > 30 && 
                30 > rsiVal.get(rsiVal.size()-1)) { return 1; }
        return 0; // by default return 0: wait
    }
    
    private static Integer sellSignal2() throws Exception {
        
        Double[] hold;
        Double StopLoss = SetUp.StopLossPoint();
        hold = client.sellPosition("btc"); if (hold[2] < StopLoss) { return -1; }
        hold = client.buyPosition("btc"); if (hold[2] < StopLoss) { return 1; }
        return 0;
    }
    
    public static void main(String[] args) throws Exception {
        
        int sig = sellSignal();
        if (sig > 0) { Trade.closeBuy(); } // buy
        if (sig < 0) { Trade.closeSell(); } // sell
        
        int sig2 = sellSignal2();
        if (sig2 > 0) { Trade.closeBuy(); }
        if (sig2 < 0) { Trade.closeSell(); }
    }
}
