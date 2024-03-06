import java.util.stream.IntStream;

public class NeuralNetwork {
    private Matrix weighMatrix;

    public NeuralNetwork(int size) {
        weighMatrix = new Matrix(size, size);
    }

    public Matrix getWeighMatrix() {
        return weighMatrix;
    }

    public void train(double[] input) throws Exception {
        double[] bipolarInput = toBipolar(input);
        Matrix biploarMatrix = Matrix.toRowMatrix(bipolarInput);
        Matrix transposeBipolarMatrix = biploarMatrix.transpose();
        Matrix multiplyMatrix = transposeBipolarMatrix.multiply(biploarMatrix);
        Matrix subtractMatrix = multiplyMatrix.subtract(Matrix.identity(weighMatrix.getData().length));
        if (Driver.mode == Driver.Mode.VERBOSE) {
            System.out.println("<-- Caluclate Contribution Matrix -->");
            System.out.println("[1] Obtain bipolar matrix for input \n" + biploarMatrix);
            System.out.println("[2] Transpose bipolar matrix:\n" + biploarMatrix);
            System.out.println("[3] (Transpose bipolar matrix) * (bipolar matrix):\n" + multiplyMatrix);
            System.out.println("[4] Contribution matrix = [3] - (Identity matrix):\n" + subtractMatrix);
            System.out.println("<-- Update Weight Matrix -->");
            System.out.println("current weight matrix :\n" + weighMatrix.toString("N", "N"));
        }
        weighMatrix = weighMatrix.add(subtractMatrix);
        if (Driver.mode == Driver.Mode.VERBOSE) {
            System.out.println("New Weight Matrix = (Contribution Matrix) + (Current Weight Matrix)\n"
                    + weighMatrix.toString("N", "N"));

        }

    }

    public double[] run(double[] input) {
        double[] bipolarInput = toBipolar(input);
        double[] output = new double[input.length];
        Matrix bipolarMatrix = Matrix.toRowMatrix(bipolarInput);
        if (Driver.mode == Driver.Mode.VERBOSE) {
            System.out.println("<-- run -->");
            System.out.println("[1] Weight matrix:\n" + weighMatrix.toString("N", "N"));
            System.out.println("[2] Obtain bipolar matrix for input:\n" + bipolarMatrix);
            System.out.println("[3] dot product bipolar matrix & each of the columns in weight matrix");
        }
        IntStream.range(0, input.length).forEach(column -> {
            try {
                Matrix columnMatrix = weighMatrix.getColumnMatrix(column);
                double dotProductResult = bipolarMatrix.dotProduct(columnMatrix);
                if (Driver.mode == Driver.Mode.VERBOSE) {
                    System.out.print("[3." + String.format("%02d", column) + ") = ");
                }
                if (dotProductResult > 0) {
                    output[column] = 1.00;
                    if (Driver.mode == Driver.Mode.VERBOSE) {
                        System.out.println(" " + dotProductResult + "   > 0  ==>  1");
                    }
                } else {
                    output[column] = 0;
                    if (Driver.mode == Driver.Mode.VERBOSE) {
                        System.out.println(" " + dotProductResult + "   <= 0  ==>  0");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return output;
    }

    static double[] toBipolar(double[] pattern) {
        double[] bipolarPattern = new double[pattern.length];
        IntStream.range(0, pattern.length).forEach(row -> {
            if (pattern[row] == 0) {
                bipolarPattern[row] = -1.00;
            } else {
                bipolarPattern[row] = 1;
            }
        });
        return bipolarPattern;
    }

    static double[] fromBipolar(double[] bipolarPattern) {
        double[] pattern = new double[bipolarPattern.length];
        IntStream.range(0, bipolarPattern.length).forEach(row -> {
            if (bipolarPattern[row] == 1.00) {
                pattern[row] = 1.00;
            } else {
                pattern[row] = 0.00;
            }
        });
        return pattern;
    }
}
