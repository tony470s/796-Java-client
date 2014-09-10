package Trader;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class DataSet {

        public static DataSource source = null;
        public static Instances instances = null;
        private Instances testDataSet = null;
        private Instances trainingDataSet = null;

        public static Instances reverse(Instances instances) {
            for (int i = 0; i < (instances.numInstances() / 2); i++) {
                instances.swap(i, instances.numInstances() - 1 - i);
            } return instances;
        }

        public static void generateDataSet(String path) {
                
                try { source = new DataSource(path); }
                catch (Exception e) { e.printStackTrace(); }

                try { instances = source.getDataSet(); } 
                catch (Exception e) { e.printStackTrace(); }

                System.out.println("\nDataset:\n");
                System.out.println(instances);
                System.out.println(instances.numInstances());
        }

        public void generateTrainingDataSet() {

                trainingDataSet = new Instances(instances);
                int size = trainingDataSet.numInstances();

                trainingDataSet.randomize(trainingDataSet.getRandomNumberGenerator(1));
                for (int i = (int) (size * 0.7); i < size; i++) {
                        trainingDataSet.remove(trainingDataSet.lastInstance());
                }
        }

        public void generateTestDataSet() {

                testDataSet = new Instances(instances);
                int size = testDataSet.numInstances();

                testDataSet.randomize(testDataSet.getRandomNumberGenerator(1));
                for (int i = 0; i < (int) (size * 0.7); i++) {
                        testDataSet.remove(testDataSet.firstInstance());
                }
        }

        public static Instances getInstances() {
                return instances;
        }

        public Instances getTrainingDataSet() {
                return trainingDataSet;
        }

        public Instances getTestDataSet() {
                return testDataSet;
        }
}