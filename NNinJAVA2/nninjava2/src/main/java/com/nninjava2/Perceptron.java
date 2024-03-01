package com.nninjava2;

public class Perceptron {
    public static final double THRESHOLD = 1;
    public static final int[][][] andData = { { { 0, 0 }, { 0 } },
            { { 0, 1 }, { 0 } },
            { { 1, 0 }, { 0 } },
            { { 1, 1 }, { 1 } } };

    public static final double LEARNING_RATE = 0.05;
    public static final double[] INITIAL_WEIGHTS = { Math.random(), Math.random(), Math.random() };

    public double calculateWeightedSum(int[] data, double[] weights) {
        double weightedSum = 0;
        for (int i = 0; i != data.length; i++) {
            weightedSum += weights[i] * data[i];
        }
        return weightedSum;
    }

    public int applyActivationFunction(double weightedSum) {
        int result = 0;
        if (weightedSum > THRESHOLD) {
            result = 1;
        }
        return result;
    }

    public double[] adjustWeights(int[] data, double[] weights, double error) {
        double[] adjustedWeights = new double[weights.length];
        for (int i = 0; i < weights.length; i++) {
            if (i < data.length) { // Ensure data array index is within bounds
                adjustedWeights[i] = LEARNING_RATE * error * data[i] + weights[i];
            }
        }
        return adjustedWeights;
    }

}