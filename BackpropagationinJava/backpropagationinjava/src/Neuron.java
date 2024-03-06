public class Neuron {
    private NeuralNetwork.LayerType layerType;
    private double threshold = 0.5 - Math.random();
    private double[] weights = { 0.5 - Math.random(), 0.5 - Math.random() };
    private double output = 0;
    private double error = 0;

    public Neuron(NeuralNetwork.LayerType layerType) {
        this.layerType = layerType;
    }

    public void applyActivationFunction(double weightedSum) {
        output = 1.0 / (1 + Math.exp(-1.0 * weightedSum));
    }

    public double derivative() {
        return output * (1.0 - output);
    }

    public NeuralNetwork.LayerType getLayerType() {
        return layerType;
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

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public String toString() {
        String returnValue = "";
        if (layerType == NeuralNetwork.LayerType.I) {
            returnValue = "(" + layerType + ": " + String.format("%.2f", output) + ")";
        } else {
            returnValue = "(" + layerType + ", " + String.format("%.2f", weights[0]) + ", "
                    + String.format("%.2f", weights[1]) + ", " + String.format("%.2f", threshold) + ", "
                    + String.format("%.5f", output);
        }
        return returnValue;
    }
}
