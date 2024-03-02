import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

public class Driver {
    public static double[][][] TRAINING_DATA = new double[][][] { { { 0, 0 }, { 0 } },
            { { 0, 1 }, { 1 } },
            { { 1, 0 }, { 1 } },
            { { 1, 1 }, { 0 } } };

    public static void main(String[] args) throws IOException {
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("->What do you want to do (train, run, exit) ?");
            String command = bufferedReader.readLine();
            if (command.equals("exit")) {
                break;
            } else if (command.equals("train")) {
                new HillClimbing().climbPerformanceHill(neuralNetwork);
            } else if (command.equals("run")) {
                double[] result = new double[] { 0, 0, 0, 0 };
                IntStream.range(0, TRAINING_DATA.length)
                        .forEach(i -> result[i] = neuralNetwork.run(TRAINING_DATA[i][0]));
                printResult(result);
            }
        }
    }

    public static void printResult(double[] result) {
        System.out.println("  Input 1   |   Input 2   |   Target Result |   Result  ");
        System.out.println("--------------------------------------------------------");
        IntStream.range(0, TRAINING_DATA.length).forEach(i -> {
            IntStream.range(0, TRAINING_DATA[0][0].length).forEach(j -> {
                System.out.print("      " + TRAINING_DATA[i][0][j] + "      |   ");
            });
            System.out.print(
                    "      " + TRAINING_DATA[i][1][0] + "      |   " + String.format("%.10f", result[i]) + "   \n");
        });
    }
}
