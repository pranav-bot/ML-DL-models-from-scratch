import java.util.stream.IntStream;

public class SimulatedAnnealing {
    static final double RATE_OF_COOLING = 0.0005;
    static final double INITIAL_TEMPERATURE = 1999;
    static final double MIN_TEMPERATURE = 0.99;
    private double temperature = INITIAL_TEMPERATURE;

    public void anneal(NeuralNetwork neuralNetwork) {
        printHeading();
        while (temperature > MIN_TEMPERATURE) {
            double currentError = neuralNetwork.calculateRMSEError();
            Neuron[] neurons = neuralNetwork.getNeurons();
            NeuralNetwork currentNetwork = new NeuralNetwork(neurons);
            double adjustedError = adjustNeurons(neuralNetwork).calculateRMSEError();
            StringBuffer sB = new StringBuffer(
                    String.format("%.6f", currentError) + "|" + String.format("%.6f", adjustedError) + "|"
                            + String.format("%.3f", temperature));
            if (adjustedError < currentError) {
                neurons = neuralNetwork.getNeurons();
                currentError = adjustedError;
                sB.append("| N/A  | N/A  | proc-Adj<curr");
            } else if (acceptNN(currentError, adjustedError, temperature, sB)) {
                neurons = neuralNetwork.getNeurons();
                currentError = adjustedError;
            }
            neuralNetwork.setNeurons(neurons);
            printRow(currentNetwork, sB);
            temperature *= 1 - RATE_OF_COOLING;
        }
    }

    private boolean acceptNN(double currentError, double adjustedError, double temperature, StringBuffer sB) {
        boolean acceptNNFlag = false;
        double acceptanceProbability = Math.exp(-((adjustedError - currentError) * 1000) / temperature);
        double randomNumb = Math.random();
        if (acceptanceProbability >= randomNumb) {
            acceptNNFlag = true;
        }
        String decision = "hold - Rand # > Prob";
        if (acceptNNFlag) {
            decision = "proc - Rand # <= Prob";
        }
        sB.append("| " + String.format("%.2f", acceptanceProbability) + " | " + String.format("%.2f", randomNumb)
                + " | " + decision);
        return acceptNNFlag;
    }

    public NeuralNetwork adjustNeurons(NeuralNetwork neuralNetwork) {
        Neuron[] neurons = new Neuron[NeuralNetwork.NUMB_OF_NEURONS];
        for (int i = 0; i < neuralNetwork.getNeurons().length; i++) {
            neurons[i] = neuralNetwork.getNeurons()[i].adjust();
        }
        neuralNetwork.setNeurons(neurons);
        return neuralNetwork;
    }

    public void printHeading() {
        System.out.print("\nUsage: Neural Network w/" + NeuralNetwork.NUMB_OF_NEURONS + " neurons ("
                + NeuralNetwork.NUMB_OF_INPUT_NEURONS + " in input layer, " + NeuralNetwork.NUMB_OF_HIDDEN_NEURONS
                + " in hidden layer, and " + "1 in output layer). Each Neuron has 2 inputs  and a threshold \n\n");
        IntStream.range(0, 34).forEach(i -> System.out.print(" "));
        System.out.print("neurons(layer-type, weight1, weight2, threshold)");
        IntStream.range(0, 35).forEach(i -> System.out.print(" "));
        System.out.println("|Curr Err| Adj Err| Temp  | Prob |Rand #| Decision");
        IntStream.range(0, 190).forEach(i -> System.out.print("-"));
        System.out.println();
    }

    private void printRow(NeuralNetwork neuralNetwork, StringBuffer sB2) {
        StringBuffer sB1 = new StringBuffer();
        IntStream.range(0, neuralNetwork.getNeurons().length).forEach(i -> {
            sB1.append("(" + neuralNetwork.getNeurons()[i].getLayerType());
            sB1.append(", " + String.format("%.1f", neuralNetwork.getNeurons()[i].getWeights()[0]));
            sB1.append(", " + String.format("%.1f", neuralNetwork.getNeurons()[i].getWeights()[1]));
            sB1.append(", " + String.format("%.1f", neuralNetwork.getNeurons()[i].getThreshold()) + ")");
            if (i < neuralNetwork.getNeurons().length - 1) {
                sB1.append(", ");
            }
        });
        IntStream.range(0, 116 - sB1.length()).forEach(i -> sB1.append(" "));
        sB1.append("|");
        System.out.println(sB1.toString() + sB2.toString());
    }

}
