import java.util.stream.IntStream;

public class Neuron {
    private double threshold = 0;
    private double[] weights = new double[] { 0, 0, 0, 0 };
    private double output = 0;
    private NeuralNetwork.LayerType layerType;

    public Neuron(NeuralNetwork.LayerType layerType) {
        this.layerType = layerType;
    }

    public Neuron(double threshold, double[] weights, NeuralNetwork.LayerType layerType) {
        this.threshold = threshold;
        this.weights = weights;
        this.layerType = layerType;
    }

    public double applyActivationFunction(double weightedSum) {
        output = 1.0 / (1 + Math.exp(-1.0 * weightedSum));
        return output;
    }

    public Neuron adjust() {
        Neuron neuron = new Neuron(this.layerType);
        IntStream.range(0, weights.length).forEach(i -> neuron.weights[i] = this.weights[i] + Math.random() - 0.5);
        neuron.threshold = threshold + Math.random() - 0.5;
        return neuron;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public NeuralNetwork.LayerType getLayerType() {
        return layerType;
    }

}
