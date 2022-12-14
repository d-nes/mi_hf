package hf3.src;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {

    public static int[][] readData() {
        List<int[]> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("train.csv"))) {
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
        double maxGain = 0;
        int bestFeature = -1;
        int bestValue = -1;

        for (int i = 0; i < features[0].length - 1; i++) { //-1 so that the labels are not included
            for (int j = 0; j < features.length; j++) {
                int nCat1 = 0;
                int nCat2 = 0;
    
                // count the number of elements in each category
                for (int k = 0; k < features.length; k++) {
                    if (features[k][i] <= features[j][i]) {
                        nCat1++;
                    } else {
                        nCat2++;
                    }
                }
    
                // calculate the entropy of each category
                double entropy1 = getEntropy(nCat1, nCat2);
                double entropy2 = getEntropy(features.length - nCat1, features.length - nCat2);
    
                // calculate the information gain
                double gain = entropy1 + entropy2;
    
                // check if this is the best gain so far
                if (gain > maxGain) {
                    maxGain = gain;
                    bestFeature = i;
                    bestValue = features[j][i];
                }
            }
        }
    
        return new int[] {bestFeature, bestValue};
    }
    
     

    public static void main(String[] args) {
        int[][] trainingData = readData();
        boolean[] labels = new boolean[trainingData.length];

        for(int i = 0; i < trainingData.length; i++){
            if(trainingData[i][8] == 1)
                labels[i] = true;
            else
                labels[i] = false;
            //labels[i] = trainingData[i][8];
        }

        int[] separation = getBestSeparation(trainingData, labels);
        //System.out.println(separation[0] + " " + separation[1]);
    }

}
