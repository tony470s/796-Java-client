package Trader;

import Index.*;
import java.util.ArrayList;

public class AutoOpen {
    
    private static Integer buySignal() {
        
        DataSet dataSet = new DataSet();
        dataSet.generateDataSet("price.csv"); // read data
        
        MACD MacdLine = new MACD(), SigLine = new MACD();
        
        MacdLine.getMACD(dataSet.getInstances(), 12, 26); 
        ArrayList<Double> macdLine = MacdLine.getMACDSet(); // macd line
        
        SigLine.getMACD(macdLine, 2, 9);
        ArrayList<Double> sigLine = SigLine.getMACDSet(); // signal linex
        
        if (sigLine.get(sigLine.size()-2) < macdLine.get(macdLine.size()-2)
                && sigLine.get(sigLine.size()-1) > macdLine.get(macdLine.size()-1)) { return 1; }
        if (sigLine.get(sigLine.size()-2) > macdLine.get(macdLine.size()-2)
                && sigLine.get(sigLine.size()-1) < macdLine.get(macdLine.size()-1)) { return -1; }
        return 0; // by default return 0: wait
    }
    
    public static void main(String[] args) throws Exception {
        
        if (SetUp.Open() == 0) { System.exit(0); } // normally closed
        
        int sig = buySignal();
        if (sig > 0) { Trade.Buy(); } // buy
        if (sig < 0) { Trade.Sell(); } // sell
    }
}
