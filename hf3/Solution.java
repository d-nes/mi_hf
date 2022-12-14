package hf3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {

    public static int[][] readData() {
        List<int[]> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("hf3\\train.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int[] intValues = new int[values.length];
                for (int i = 0; i < values.length; i++) {
                    intValues[i] = Integer.parseInt(values[i]);
                }
                lines.add(intValues);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines.toArray(new int[0][]);
    }

    public static double getEntropy(int nCat1, int nCat2) {
        if(nCat1 == 0 || nCat2 == 0)
            return 0.0;
        else{
            double p1 = (double) nCat1 / (nCat1 + nCat2);
            double p2 = (double) nCat2 / (nCat1 + nCat2);
            return -p1 * (Math.log(p1) / Math.log(2)) - p2 * (Math.log(p2) / Math.log(2));
        }
    }

    public static int[] getBestSeparation(int[][] features, boolean[] labels) {
        int[] answer = { 0, 0 };

        return answer;
    }  

    public static void main(String[] args) {
        int[][] trainingData = readData();
        
    }

}
