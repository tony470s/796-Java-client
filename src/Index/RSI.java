package Index;

import java.util.ArrayList;
import java.util.List;

import weka.core.Attribute;
import weka.core.Instances;

// Relative Strength Index
public class RSI {

        ArrayList<Double> rsi = new ArrayList<>();
        public RSI() {
                super();
        }

        public void getRSI(Instances instances, int period) {
                List RSIList = new ArrayList(3);
                RSIList.add("overbought");
                RSIList.add("oversold");
                RSIList.add("neither");
                Attribute RSI = new Attribute("RSI", RSIList);
                instances.insertAttributeAt(RSI, instances.numAttributes() - 1);

                ArrayList<Double> change = new ArrayList<>();
                ArrayList<Double> gain = new ArrayList<>();
                ArrayList<Double> loss = new ArrayList<>();
                ArrayList<Double> averageGain = new ArrayList<>();
                ArrayList<Double> averageLoss = new ArrayList<>();
                double gainSum = 0;
                double lossSum = 0;

                for (int i = 0; i < instances.numInstances(); i++) {
                        change.add(0.0);
                        gain.add(0.0);
                        loss.add(0.0);
                        averageGain.add(0.0);
                        averageLoss.add(0.0);
                        rsi.add(0.0);
                }

                // Calculate "change", "gain" and "loss"
                for (int i = 1; i < instances.numInstances(); i++) {
                        change
                                        .set(i, instances.get(i).value(
                                                        instances.numAttributes() - 7)
                                                        - instances.get(i - 1).value(
                                                                        instances.numAttributes() - 7));
                        if (change.get(i) > 0) {
                                gain.set(i, change.get(i));
                                loss.set(i, 0.0);
                        } else if (change.get(i) < 0) {
                                loss.set(i, change.get(i) * (-1.0));
                                gain.set(i, 0.0);
                        }
                }

                // Calculate first "average gain" and "average loss"
                for (int i = 0; i < period; i++) {
                        gainSum += gain.get(i);
                }
                for (int i = 0; i < loss.size(); i++) {
                        lossSum += loss.get(i);
                }
                averageGain.set(period, gainSum / ((double) period));
                averageLoss.set(period, lossSum / ((double) period));

                // Calculate subsequent "average gain" and "average loss" values
                for (int i = period + 1; i < instances.numInstances(); i++) {
                        averageGain.set(i, (averageGain.get(i - 1)
                                        * ((double) (period - 1)) + gain.get(i))
                                        / ((double) period));
                        averageLoss.set(i, (averageLoss.get(i - 1)
                                        * ((double) (period - 1)) + loss.get(i))
                                        / ((double) period));
                }

                double RS = 0;
                double RSIndex = 0;
                for (int i = period; i < instances.numInstances(); i++) {
                        RS = averageGain.get(i) / averageLoss.get(i);
                        RSIndex = 100 - 100 / (1 + RS);
                        rsi.set(i, RSIndex);
                }
                for (int i = period; i < instances.numInstances(); i++) {
                        if (rsi.get(i) > 70.0) {
                                instances.get(i).setValue(instances.numAttributes() - 2, "overbought");
                        } else if (rsi.get(i) < 30.0) {
                                instances.get(i).setValue(instances.numAttributes() - 2, "oversold");
                        } else if (rsi.get(i) >= 30 && rsi.get(i) <= 70) {
                                instances.get(i).setValue(instances.numAttributes() - 2, "neither");
                        }
                }
        }

        public ArrayList<Double> getRSISet() {
                return rsi;
        }
}