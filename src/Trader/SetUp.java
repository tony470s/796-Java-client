package Trader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetUp {
    
    public static Double StopLossPoint() throws Exception {
        
        String path = new File("setup.ini").getAbsolutePath();
        try (BufferedReader reader = new BufferedReader(new FileReader(path));) { 
            String Line = reader.readLine();
            while (Line != null) {
                try {
                    Pattern stopLoss = Pattern.compile("stoploss.*=(.+)");
                    Matcher m = stopLoss.matcher(Line.toLowerCase()); m.find();
                    return Double.valueOf(m.group(1));
                } catch (Exception e) {}
                Line = reader.readLine();
            }
        } return -25.;
    }
    
    public static Double Kelly() throws Exception {
        
        String path = new File("setup.ini").getAbsolutePath();
        try (BufferedReader reader = new BufferedReader(new FileReader(path));) { 
            String Line = reader.readLine(); 
            while (Line != null) {
                try {
                    Pattern stopLoss = Pattern.compile("kelly.*=(.+)");
                    Matcher m = stopLoss.matcher(Line.toLowerCase()); m.find();
                    return Double.valueOf(m.group(1));
                } catch (Exception e) {}
                Line = reader.readLine();
            }
        } return 0.25;
    }
    
    public static Integer Leverage() throws Exception {
        
        String path = new File("setup.ini").getAbsolutePath();
        try (BufferedReader reader = new BufferedReader(new FileReader(path));) { 
            String Line = reader.readLine(); 
            while (Line != null) {
                try {
                    Pattern stopLoss = Pattern.compile("leverage.*=(.+)");
                    Matcher m = stopLoss.matcher(Line.toLowerCase()); m.find();
                    return Integer.valueOf(m.group(1));
                } catch (Exception e) {}
                Line = reader.readLine();
            }
        } return 10;
    }
    
    public static Integer Open() throws Exception {
        
        String path = new File("setup.ini").getAbsolutePath();
        try (BufferedReader reader = new BufferedReader(new FileReader(path));) { 
            String Line = reader.readLine(); 
            while (Line != null) {
                try {
                    Pattern stopLoss = Pattern.compile("auto-open.*=(.+)");
                    Matcher m = stopLoss.matcher(Line.toLowerCase()); m.find();
                    return Integer.valueOf(m.group(1));
                } catch (Exception e) {}
                Line = reader.readLine();
            }
        } return 1;
    }
    
    public static Integer Close() throws Exception {
        
        String path = new File("setup.ini").getAbsolutePath();
        try (BufferedReader reader = new BufferedReader(new FileReader(path));) { 
            String Line = reader.readLine(); 
            while (Line != null) {
                try {
                    Pattern stopLoss = Pattern.compile("auto-close.*=(.+)");
                    Matcher m = stopLoss.matcher(Line.toLowerCase()); m.find();
                    return Integer.valueOf(m.group(1));
                } catch (Exception e) {}
                Line = reader.readLine();
            }
        } return 1;
    }
}
