package com.nninjava2;

import java.util.Arrays;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Driver extends Application {
    static double m = 0;
    static double b = 0;
    int[][][] data = Perceptron.andData;

    public static void main(String[] args) {
        int[][][] data = Perceptron.andData;
        double[] weights = Perceptron.INITIAL_WEIGHTS;
        Driver driver = new Driver();
        Perceptron perceptron = new Perceptron();
        int epochNumber = 0;
        boolean errorFlag = true;
        double error = 0;
        double[] adjustedWeights = null;
        int[][] a = Perceptron.andData[0];
        System.out.println(Arrays.toString(a));
        while (errorFlag) {
            driver.printHeading(epochNumber++);
            errorFlag = false;
            error = 0;
            for (int i = 0; i != data.length; i++) {
                double weightedSum = perceptron.calculateWeightedSum(data[i][0], weights);
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
        m = -weights[1] / weights[0];
        b = Perceptron.THRESHOLD / weights[0];
        System.out.println("\ny = " + String.format("%.2f", m) + "x " + String.format("%.2f", b));
        launch(args);
    }

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws Exception {
        stage.setTitle("NN in Java");
        XYChart.Series<Number, Number> series1 = new XYChart.Series<Number, Number>();
        series1.setName("zero");
        XYChart.Series<Number, Number> series2 = new XYChart.Series<Number, Number>();
        series2.setName("one");
        IntStream.range(0, data.length).forEach(i -> {
            int x = Perceptron.andData[i][0][0];
            int y = Perceptron.andData[i][0][1];
            if (Perceptron.andData[i][1][0] == 0) {
                series1.getData().add(new XYChart.Data<Number, Number>(x, y));
            } else {
                series2.getData().add(new XYChart.Data<Number, Number>(x, y));
            }
        });
        double x1 = 0;
        double y1 = b;
        double x2 = -(b / m);
        double y2 = 0;
        String title = new String("y = " + String.format("%.2f", m) + "x + " + String.format("%.2f", b) + " |   (0, "
                + String.format("%.2f", x2) + ",  0)"); // Updated x2 to properly represent the line
        NumberAxis xAxis = new NumberAxis(0, 4, 1);
        NumberAxis yAxis = new NumberAxis(0, 4, 1);
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle(title);
        scatterChart.getData().add(series1);
        scatterChart.getData().add(series2);

        XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
        series3.getData().add(new XYChart.Data<>(x1, y1));
        series3.getData().add(new XYChart.Data<>(x2, y2));
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.getData().add(series3);
        lineChart.setOpacity(0.1);
        Pane pane = new Pane();
        pane.getChildren().addAll(scatterChart, lineChart);
        stage.setScene(new Scene(pane, 500, 400));
        stage.show();
    }

    public void printHeading(int epochNumber) {
        System.out.println("\n========================================================Epoch # " + epochNumber
                + "================================================");
        System.out.println(
                "   w0  |    w1  |   w2  | x0  |  x1  |   x2  |   Target Result   |   Result  |   error   |   Weighted Sum    | adjusted w0 |  adjusted w1 |   adjusted w2");
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void printVector(int[][] data, double[] weights, int result, double error, double weightedSum,
            double[] adjustedWeights) {
        String w0 = String.format("%.2f", weights[0]);
        if (weights[0] >= 0) {
            w0 = " " + w0;
        }
        String w1 = String.format("%.2f", weights[1]);
        if (weights[1] >= 0) {
            w1 = " " + w1;
        }
        String w2 = String.format("%.2f", weights[2]);
        if (weights[2] >= 0) {
            w2 = " " + w2;
        }
        String stringError = Double.toString(error);
        if (error >= 0) {
            stringError = " " + stringError;
        }
        String stringWeightedSum = String.format("%.2f", weightedSum);
        if (weightedSum >= 0) {
            stringWeightedSum = " " + stringWeightedSum;
        }
        String adjusedW0 = String.format("%.2f", adjustedWeights[0]);
        if (adjustedWeights[0] >= 0) {
            adjusedW0 = " " + adjusedW0;
        }
        String adjusedW1 = String.format("%.2f", adjustedWeights[1]);
        if (adjustedWeights[1] >= 0) {
            adjusedW1 = " " + adjusedW1;
        }
        String adjusedW2 = String.format("%.2f", adjustedWeights[2]);
        if (adjustedWeights[2] >= 0) {
            adjusedW2 = " " + adjusedW2;
        }
        System.out.println("  " + w0 + " | " + w1 + " | " +
                " |   " + data[0][0] + " | " + data[0][1] +
                "      |   " + data[1][0] + "  | " + result + "   |      " +
                stringError +
                " |     " + stringWeightedSum + "    | " +
                adjusedW0 + adjusedW1 + "    | " + adjusedW2);
    }

}