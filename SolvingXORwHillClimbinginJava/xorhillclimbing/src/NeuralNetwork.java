import java.util.stream.IntStream;

public class NeuralNetwork {
    static enum LayerType {
        I, H, O
    };

    final static int NUMB_OF_INPUT_NEURONS = 2;
    final static int NUMB_OF_HIDDEN_NEURONS = 2;
    final static int NUMB_OF_NEURONS = NUMB_OF_HIDDEN_NEURONS + NUMB_OF_INPUT_NEURONS + 1;
    private Neuron[] neurons = new Neuron[NUMB_OF_NEURONS];

    public NeuralNetwork() {
        double[] weights;
        for (int i = 0; i < NUMB_OF_INPUT_NEURONS; i++) {
            weights = new double[] { Math.random() - 0.5, Math.random() - 0.5 };
            neurons[i] = new Neuron(Math.random() - 0.5, weights, LayerType.I);

        }
        for (int i = NUMB_OF_INPUT_NEURONS; i < NUMB_OF_HIDDEN_NEURONS + NUMB_OF_INPUT_NEURONS; i++) {
            weights = new double[] { Math.random() - 0.5, Math.random() - 0.5 };
            neurons[i] = new Neuron(Math.random() - 0.5, weights, LayerType.H);
        }
        weights = new double[] { Math.random() - 0.5, Math.random() - 0.5 };
        neurons[NUMB_OF_NEURONS - 1] = new Neuron(Math.random() - 0.5, weights, LayerType.O);
    }

    public NeuralNetwork(Neuron[] neurons) {
        IntStream.range(0, neurons.length).forEach(i -> this.neurons[i] = new Neuron(neurons[i].getThreshold(),
                neurons[i].getWeights(), neurons[i].getLayerType()));
    }

    public double run(double[] input) {
        int k = 0;
        for (int i = 0; i < neurons.length; i++) {
            double weightedSum;
            switch (neurons[i].getLayerType()) {
                case I:
                    neurons[i].setOutput(input[i]);
                    break;
                case H:
                    k = 0;
                    weightedSum = -neurons[i].getThreshold();
                    for (int j = 0; j < neurons.length; j++) {
                        if (neurons[j].getLayerType() == LayerType.I) {
                            weightedSum += neurons[i].getWeights()[k++] * neurons[j].getOutput();
                        }
                        neurons[i].applyActivationFunction(weightedSum);
                    }
                    break;
                case O:
                    k = 0;
                    weightedSum = -neurons[i].getThreshold();
                    for (int j = 0; j < neurons.length; j++) {
                        if (neurons[j].getLayerType() == LayerType.H) {
                            weightedSum += neurons[i].getWeights()[k++] * neurons[j].getOutput();
                        }
                        neurons[i].applyActivationFunction(weightedSum);
                    }
                    break;
            }
        }
        return neurons[NUMB_OF_NEURONS - 1].getOutput();
    }

    public double calculateRMSEError() {
        double localErrors = 0;
        for (int i = 0; i < Driver.TRAINING_DATA.length; i++) {
            double result = run(Driver.TRAINING_DATA[i][0]);
            localErrors += (Driver.TRAINING_DATA[i][1][0] - result) * (Driver.TRAINING_DATA[i][1][0] - result);
        }
        return Math.sqrt(localErrors / Driver.TRAINING_DATA.length);
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public void setNeurons(Neuron[] neurons) {
        this.neurons = neurons;
    }
}
