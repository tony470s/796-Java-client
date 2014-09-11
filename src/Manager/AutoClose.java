package Manager;

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
    
    public static void run() throws Exception {
        
        if (SetUp.Close() == 0) { System.exit(0); } // normally closed
        
        Thread t1, t2;
        
        t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    int sig = sellSignal();
                    if (sig > 0) { Trade.closeBuy(); }
                    if (sig < 0) { Trade.closeSell(); } // sell
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
        
        t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    int sig = sellSignal2();
                    if (sig > 0) { Trade.closeBuy(); }
                    if (sig < 0) { Trade.closeSell(); }
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
        
        t1.start(); t2.start();
    }
}
