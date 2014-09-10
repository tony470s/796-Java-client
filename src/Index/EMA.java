package Index;

import java.util.ArrayList;
import weka.core.Instances;

public class EMA {
    
    ArrayList<Double> EMASet = new ArrayList<>();

    private static Double valueOf(Instances in, Integer i, Integer j) {
        return Double.valueOf(in.get(i).toString(j));
    }
    
    public EMA() { super(); }

    public void getEMA(Instances instances, int Duration) {
        
        if (instances.numInstances() >= Duration) {
            
            ArrayList<Double> AVGSet = new ArrayList<>();
            double weights = (double) (2 / ((double) Duration + 1));
            double ema, tempSum = 0, average = 0;

            for (int i = 0; i < Duration - 1; i++) {
                for (int j = 0; j <= i; j++) {
                    tempSum += valueOf(instances, i, 0) + valueOf(instances, j, 0);
                    average = tempSum / (j + 1);
                }
                AVGSet.add(i, average); tempSum = 0;
            }

            for (int i = 0; i < Duration; i++) {
                ema = (valueOf(instances, i, 0) - AVGSet.get(i - 1)) * weights 
                    + AVGSet.get(i - 1);
                EMASet.add(i, ema);
            }
        }
    }
    
    public void getEMA(ArrayList<Double> List, int Duration) {
        
        if (List.size() >= Duration) {
            
            ArrayList<Double> AVGSet = new ArrayList<>();
            double weights = (double) (2 / ((double) Duration + 1));
            double ema, tempSum = 0, average = 0;

            for (int i = 0; i < Duration - 1; i++) {
                for (int j = 0; j <= i; j++) {
                    tempSum += List.get(i) + List.get(j);
                    average = tempSum / (j + 1);
                }
                AVGSet.add(i, average); tempSum = 0;
            }

            for (int i = 0; i < Duration; i++) {
                ema = (List.get(i) - AVGSet.get(i - 1)) * weights 
                    + AVGSet.get(i - 1);
                EMASet.add(i, ema);
            }
        }
    }
    
    public ArrayList<Double> getEMASet() {
        return EMASet;
    }
}