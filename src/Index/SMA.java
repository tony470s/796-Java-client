package Index;

import java.util.ArrayList;
import weka.core.Instances;

public class SMA {
    
    ArrayList<Double> SMASet = new ArrayList<>();
    
    private static Double valueOf(Instances in, Integer i, Integer j) {
        return Double.valueOf(in.get(i).toString(j));
    }
    public SMA() { super(); }

    public void getSMA(Instances instances, int Duration) {

        if (instances.numInstances() >= Duration) {

            ArrayList<Double> AVGSet = new ArrayList<>();
            double sma, avg, sum = 0;

            for (int i = 0; i < Duration; i++) {
                sum += valueOf(instances, i, 0);
            }
            avg = sum / Duration; sum = 0;
            AVGSet.add(Duration - 1, avg);
            
            for (int i = 0; i < Duration; i++) {
                sum += AVGSet.get(i);
            }
            sma = sum / Duration;
            SMASet.add(Duration - 1, sma);
        }
    }
    
    public void getSMA(ArrayList<Double> List, int Duration) {

        if (List.size() >= Duration) {

            ArrayList<Double> AVGSet = new ArrayList<>();
            double sma, avg, sum = 0;

            for (int i = 0; i < Duration; i++) {
                sum += List.get(i);
            }
            avg = sum / Duration; sum = 0;
            AVGSet.add(Duration - 1, avg);
            
            for (int i = 0; i < Duration; i++) {
                sum += AVGSet.get(i);
            }
            sma = sum / Duration;
            SMASet.add(Duration - 1, sma);
        }
    }
    
    public ArrayList<Double> getSMASet() {
        return SMASet;
    }
}