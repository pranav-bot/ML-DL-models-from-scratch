import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.StringCharacterIterator;

public class Driver {
    static final int NUMB_OF_ROWS_IN_DRAWING_BOARD = 5;

    enum Mode {
        DEFAULT, VERBOSE
    };

    static Driver.Mode mode = Mode.DEFAULT;

    public static void main(String[] args) throws Exception {
        System.out.println(">Enter # of neurons (must be divisible by 5 - [# of neurons]=[# of columns]X [5 rows]): ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.valueOf(bufferedReader.readLine());
        NeuralNetwork neuralNetwork = new NeuralNetwork(size);
        double[] input = new double[size];
        double[] output = new double[size];
        boolean flag = true;
        while (flag) {
            System.out.println(">What do you want to do(train, run, clear, change_mode, exit) ?");
            String command = bufferedReader.readLine();
            switch (command) {
                case "train":
                    System.out.println("> Provide training pattern: ");
                    input = getInput(new StringCharacterIterator(bufferedReader.readLine()), size);
                    neuralNetwork.train(input);
                    System.out.println(Matrix.getMatrix(input, NUMB_OF_ROWS_IN_DRAWING_BOARD).toPackedString());
                    System.out.println("training done on above pattern");
                    break;
                case "run":
                    System.out.println("> Provide training pattern: ");
                    input = getInput(new StringCharacterIterator(bufferedReader.readLine()), size);
                    output = neuralNetwork.run(input);
                    System.out.println("Input pattern:");
                    System.out.print(Matrix.getMatrix(input, NUMB_OF_ROWS_IN_DRAWING_BOARD).toPackedString());
                    System.out.println("output pattern");
                    System.out.println(Matrix.getMatrix(output, NUMB_OF_ROWS_IN_DRAWING_BOARD).toPackedString());
                    break;
                case "clear":
                    neuralNetwork.getWeighMatrix().clear();
                    break;
                case "change_mode":
                    System.out.println("> Specify running mode (default, verbose) ?");
                    if (bufferedReader.readLine().equals("verbose")) {
                        mode = Mode.VERBOSE;
                    } else {
                        mode = Mode.DEFAULT;
                    }
                    break;
                case "exit":
                    flag = false;
                    break;
            }
        }
        System.exit(0);
    }

    static double[] getInput(StringCharacterIterator iterator, int size) {
        double[] input = new double[size];
        while (iterator.getIndex() < iterator.getEndIndex()) {
            input[iterator.getIndex()] = Double.parseDouble(String.valueOf(iterator.current()));
            iterator.next();
        }
        return input;
    }
}
