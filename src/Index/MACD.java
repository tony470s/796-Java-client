package Index;

import java.util.ArrayList;
import weka.core.Instances;

public class MACD {
    
    ArrayList<Double> MACDSet = new ArrayList<>();
    
    public MACD() { super(); }

    public void getMACD(Instances instances, Integer fast, Integer slow) {
        
        EMA EMA_Fast = new EMA();
        EMA_Fast.getEMA(instances, fast);
        ArrayList<Double> EMA_FastSet = EMA_Fast.getEMASet();

        EMA EMA_Slow = new EMA();
        EMA_Slow.getEMA(instances, slow);
        ArrayList<Double> EMA_SlowSet = EMA_Slow.getEMASet();

        for (int i = 0; i < EMA_FastSet.size(); i++) {
            MACDSet.add(i, EMA_FastSet.get(i) - EMA_SlowSet.get(i));
        }
    }
    
    public void getMACD(ArrayList<Double> List, Integer fast, Integer slow) {
        
        EMA EMA_Fast = new EMA();
        EMA_Fast.getEMA(List, fast);
        ArrayList<Double> EMA_FastSet = EMA_Fast.getEMASet();
        
        EMA EMA_Slow = new EMA();
        EMA_Slow.getEMA(List, slow);
        ArrayList<Double> EMA_SlowSet = EMA_Slow.getEMASet();

        for (int i = 0; i < EMA_FastSet.size(); i++) {
            MACDSet.add(i, EMA_FastSet.get(i) - EMA_SlowSet.get(i));
        }
    }

    public ArrayList<Double> getMACDSet() {
        return MACDSet;
    }
}