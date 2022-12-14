package hf3.src; //TODO delete for upload

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {

    public static int[][] readData(String filepath) {
        List<int[]> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
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
        if (nCat1 == 0 || nCat2 == 0)
            return 0.0;
        else {
            double p1 = (double) nCat1 / (nCat1 + nCat2);
            double p2 = (double) nCat2 / (nCat1 + nCat2);
            return -p1 * (Math.log(p1) / Math.log(2)) - p2 * (Math.log(p2) / Math.log(2));
        }
    }

    public static int[] getBestSeparation(int[][] features, boolean[] labels) {
        double maxGain = 0;
        int bestFeature = -1;
        int bestValue = -1;
    
        for (int i = 0; i < features[0].length - 1; i++) { // -1 so that the labels are not included
            
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
                //double gain = entropy1 + entropy2;
                double p1 = (double) nCat1 / (nCat1 + nCat2);
                double p2 = (double) nCat2 / (nCat1 + nCat2);
                double gain = getEntropy(nCat1 + nCat2, 0) - p1 * entropy1 - p2 * entropy2;

    
                // check if this is the best gain so far
                if (gain > maxGain) {
                    maxGain = gain;
                    bestFeature = i;
                    bestValue = features[j][i];
                }
            }
        }
    
        return new int[] { bestFeature, bestValue };
    }
    
    
    static class Node {
        int feature;
        int value;
        boolean label;
        Node left;
        Node right;

        public Node(int feature, int value) {
            this.feature = feature;
            this.value = value;
        }

        public Node(int feature, int value, boolean label) {
            this(feature, value);
            this.label = label;
        }
    }

    static class DecisionTree {

        private Node root;

        public DecisionTree(int[][] features, boolean[] labels) {
            this.root = buildTree(features, labels);
        }

        private Node buildTree(int[][] features, boolean[] labels) {
            int[] bestSplit = getBestSeparation(features, labels);
            int splitFeature = bestSplit[0];
            int splitValue = bestSplit[1];

            if(splitFeature < 0 || splitValue < 0){
                return null;
            }

            Node root = new Node(splitFeature, splitValue);

            // Split the dataset into left and right branches based on the best split
            int[][] leftFeatures = new int[features.length][];
            int[][] rightFeatures = new int[features.length][];
            boolean[] leftLabels = new boolean[labels.length];
            boolean[] rightLabels = new boolean[labels.length];

            int leftIndex = 0;
            int rightIndex = 0;
            for (int i = 0; i < features.length; i++) {
                if (features[i][splitFeature] <= splitValue) {
                    leftFeatures[leftIndex] = features[i];
                    leftLabels[leftIndex] = labels[i];
                    leftIndex++;
                } else {
                    rightFeatures[rightIndex] = features[i];
                    rightLabels[rightIndex] = labels[i];
                    rightIndex++;
                }
            }

            // Trim the arrays to the correct size
            leftFeatures = Arrays.copyOfRange(leftFeatures, 0, leftIndex);
            rightFeatures = Arrays.copyOfRange(rightFeatures, 0, rightIndex);
            leftLabels = Arrays.copyOfRange(leftLabels, 0, leftIndex);
            rightLabels = Arrays.copyOfRange(rightLabels, 0, rightIndex);

            // Recursively build the left and right subtrees
            root.left = buildTree(leftFeatures, leftLabels);
            root.right = buildTree(rightFeatures, rightLabels);

            return root;
        }

        public boolean predict(int[] data) {
            if(this.root == null) return false;
            Node current = root;
            while (current.left != null && current.right != null) {
                if (data[current.feature] <= current.value) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            return current.label;
        }
    }

    public static void main(String[] args) {
        int[][] trainingData = readData("hf3\\train.csv");
        boolean[] labels = new boolean[trainingData.length];

        for (int i = 0; i < trainingData.length; i++) {
            if (trainingData[i][8] == 1)
                labels[i] = true;
            else
                labels[i] = false;
        }

        //int[] separation = getBestSeparation(trainingData, labels);
        // System.out.println(separation[0] + " " + separation[1]);
        DecisionTree tree = new DecisionTree(trainingData, labels);

        int[][] testData = readData("hf3\\test.csv");

        for(int[] i : testData)
            System.out.println(tree.predict(i));
    }

}
