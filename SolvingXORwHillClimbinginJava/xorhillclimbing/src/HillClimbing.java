import java.util.stream.IntStream;

public class HillClimbing {
    static final int NUMB_OF_ITERATIONS_BEFORE_MAXIMA = 10000;

    private void printHeading() {
        System.out.print("\nUsage = Neural Network w/ " + NeuralNetwork.NUMB_OF_NEURONS + " Neurons ("
                + NeuralNetwork.NUMB_OF_INPUT_NEURONS + " in input layer" + NeuralNetwork.NUMB_OF_HIDDEN_NEURONS
                + " in hidden layer, and " + "1 in output layer). Each neuron has 2 inputs and a threshold \n\n");
        IntStream.range(0, 37).forEach(i -> System.out.print(" "));
        System.out.print("neurons(layer-type, weight1, weight2, threshold)");
        IntStream.range(0, 37).forEach(i -> System.out.print(" "));
        System.out.println("|Current Err|Adjusted Err|Decision");
        IntStream.range(0, 165).forEach(i -> System.out.print("-"));
        System.out.println();
    }

    public void climbPerformanceHill(NeuralNetwork network) {
        printHeading();
        int iterToMaximaCounter = 1;
        while (iterToMaximaCounter < NUMB_OF_ITERATIONS_BEFORE_MAXIMA) {
            double currentError = network.calculateRMSEError();
            Neuron[] neurons = network.getNeurons();
            NeuralNetwork currentNetwork = new NeuralNetwork(neurons);
            double adjustedError = adjustNeurons(network).calculateRMSEError();
            StringBuffer sb = new StringBuffer(
                    String.format("%.9f", currentError) + "|" + String.format("%.10f", adjustedError));
            if (adjustedError < currentError) {
                sb.append("|climb - iter # " + iterToMaximaCounter);
                currentError = adjustedError;
                neurons = network.getNeurons();
                iterToMaximaCounter = 1;
            } else {
                if (iterToMaximaCounter == NUMB_OF_ITERATIONS_BEFORE_MAXIMA) {
                    sb.append("potential maxima");
                } else {
                    sb.append("| stay - iter # " + iterToMaximaCounter);
                }
                network.setNeurons(neurons);
                iterToMaximaCounter++;
            }
            printRow(currentNetwork, sb);

        }
    }

    public NeuralNetwork adjustNeurons(NeuralNetwork nN) {
        Neuron[] neurons = new Neuron[NeuralNetwork.NUMB_OF_NEURONS];
        for (int i = 0; i < nN.getNeurons().length; i++) {
            neurons[i] = nN.getNeurons()[i].adjust();
        }
        nN.setNeurons(neurons);
        return nN;
    }

    private void printRow(NeuralNetwork nN, StringBuffer sB2) {
        StringBuffer sB1 = new StringBuffer();
        IntStream.range(0, nN.getNeurons().length).forEach(i -> {
            sB1.append("(" + nN.getNeurons()[i].getLayerType());
            sB1.append(", " + String.format("%.1f", nN.getNeurons()[i].getWeights()[0]));
            sB1.append(", " + String.format("%.1f", nN.getNeurons()[i].getWeights()[1]));
            sB1.append(", " + String.format("%.1f", nN.getNeurons()[i].getThreshold()));
            if (i < nN.getNeurons().length - 1) {
                sB1.append(", ");
            }
        });
        IntStream.range(0, 122 - sB1.length()).forEach(i -> sB1.append(" "));
        sB1.append("|");
        System.out.println(sB1.toString() + sB2.toString());
    }

}
