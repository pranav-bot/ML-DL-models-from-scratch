public class Perceptron {
    public static final double THRESHOLD = 0.0;
    public static final int[][][] TRAINING_DATA = { { { 1, 1, 1, 1 }, { 1 } },
            { { 1, 2, 2, 2 }, { 1 } },
            { { 1, 1, -1, -1 }, { 0 } },
            { { 1, 2, -2, -2 }, { 0 } } };
    public static final double LEARNING_RATE = 0.05;
    public static final double[] INITIAL_WEIGHTS = { Math.random(), Math.random(), Math.random(), Math.random() };

    public double calculatedWeightedSum(int[] data, double[] weights) {
        double weightedSum = 0;
        for (int i = 0; i < data.length; i++) {
            weightedSum += weights[i] * data[i];
        }
        return weightedSum;
    }

    public int applyActivationFunction(double weightedSum) {
        return (weightedSum > THRESHOLD) ? 1 : 0;
    }

    public double[] adjustWeights(int[] data, double[] weights, double error) {
        double[] adjustedWeights = new double[weights.length];
        for (int i = 0; i < weights.length; i++) {
            adjustedWeights[i] = LEARNING_RATE * error * data[i] + weights[i];
        }
        return adjustedWeights;

    }

}
