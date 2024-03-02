import java.util.stream.IntStream;

public class Driver {
    static int[][][] data = Perceptron.TRAINING_DATA;

    public static void main(String[] args) {
        double[] weights = Perceptron.INITIAL_WEIGHTS;
        Driver driver = new Driver();
        Perceptron perceptron = new Perceptron();
        int epochNumber = 0;
        boolean errorFlag = false;
        double error = 0;
        double[] adjustedWeights = null;
        while (errorFlag) {
            driver.printHeading(epochNumber++, weights.length);
            errorFlag = false;
            error = 0;
            for (int i = 0; i < data.length; i++) {
                double weightedSum = perceptron.calculatedWeightedSum(data[i][0], weights);
                int result = perceptron.applyActivationFunction(weightedSum);
                error = data[i][1][0] - result;
                if (error != 0) {
                    errorFlag = true;
                }
                adjustedWeights = perceptron.adjustWeights(data[i][0], weights, error);
                driver.printVector(data[i], weights, result, error, weightedSum, adjustedWeights);
                weights = adjustedWeights;
            }
        }
        System.out.println("\n> Plane Equation: " + String.format("%.2f", weights[1]) + "x + "
                + String.format("%.2f", weights[2]) + "y + " + String.format("%.2f", weights[3]) + "z + "
                + String.format("%.2f", weights[0] - Perceptron.THRESHOLD) + " = 0");
    }

    public void printHeading(int epochNumber, int numbOfWeights) {
        System.out.print("\n");
        IntStream.range(0, 64).forEach(i -> System.out.print("="));
        System.out.println("epoch #" + epochNumber);
        IntStream.range(0, 64).forEach(i -> System.out.print("="));
        System.out.println();
        IntStream.range(0, numbOfWeights).forEach(i -> System.out.print(" w" + i++ + "   |"));
        System.out.print(
                "  x0  |   x   |   y   |   z   |   Target Result   |   Result  |   error   |   Weighted Sum    |");
        IntStream.range(0, numbOfWeights - 1).forEach(i -> System.out.print("adjusted w" + i++ + "|"));
        System.out.println("adjusted w" + (numbOfWeights - 1));
        IntStream.range(0, 137).forEach(i -> System.out.print("-"));
        System.out.println();
    }

    public void printVector(int[][] data, double[] weights, int result, double error, double weightedSum,
            double[] adjustedWeights) {
        String[] stringWeights = new String[weights.length];
        for (int i = 0; i < weights.length; i++) {
            stringWeights[i] = String.format("%.2f", weights[i]);
            if (weights[i] >= 0) {
                stringWeights[i] = " " + stringWeights[i];
            }
        }
        String[] stringData = new String[data[0].length];
        for (int i = 0; i < stringData.length; i++) {
            stringData[i] = Integer.toString(data[0][i]);
            if (data[0][i] >= 0) {
                stringData[i] = " " + stringData[i];
            }
        }
        String stringError = Double.toString(error);
        if (error >= 0) {
            stringError = " " + stringError;
        }
        String stringWeightedSum = String.format("%.2f", weightedSum);
        if (weightedSum >= 0) {
            stringWeightedSum = " " + stringWeightedSum;
        }
        String[] stringAdjustedWeights = new String[adjustedWeights.length];
        for (int i = 0; i < adjustedWeights.length; i++) {
            stringAdjustedWeights[i] = String.format("%.2f", adjustedWeights[i]);
            if (adjustedWeights[i] >= 0) {
                stringAdjustedWeights[i] = " " + stringAdjustedWeights[i];
            }
        }
        IntStream.range(0, stringWeights.length).forEach(i -> System.out.print(stringWeights[i] + " |"));
        System.out.print(" ");
        IntStream.range(0, stringData.length).forEach(i -> System.out.print(stringData[i] + " |"));
        System.out.print("      " + data[1][0] + "  |   " + result + "  |   " + stringError + "  |   "
                + stringWeightedSum + "  |   ");
        IntStream.range(0, stringAdjustedWeights.length - 1)
                .forEach(i -> System.out.print(stringAdjustedWeights[i] + " |"));
        System.out.println(stringAdjustedWeights[stringAdjustedWeights.length - 1]);
    }
}