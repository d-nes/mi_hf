package hf3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Solution {

    static int[][] trainingData = new int[1000][10];

    public static void readData() {
        Scanner sc;
        try {
            sc = new Scanner(new File("hf3\\train.csv")); // TODO
            int lines = 0;
            while (sc.hasNext()) {
                String[] values = sc.next().split(",");
                for(int i = 0; i < values.length; i++){
                    trainingData[lines++][i] = Integer.parseInt(values[i]);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
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
        readData();
        System.out.println(trainingData);
    }

}
