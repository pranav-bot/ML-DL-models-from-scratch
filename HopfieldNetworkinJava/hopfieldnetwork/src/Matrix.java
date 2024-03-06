import java.util.stream.IntStream;

public class Matrix {
    private double data[][];

    enum ScalorOperation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    };

    public Matrix(int rows, int columns) {
        data = new double[rows][columns];
    }

    public Matrix(double[][] data) {
        this.data = new double[data.length][data[0].length];
        IntStream.range(0, this.data.length).forEach(row -> IntStream.range(0, this.data[0].length)
                .forEach(column -> this.data[row][column] = data[row][column]));
    }

    public double[][] getData() {
        return data;
    }

    public static Matrix getMatrix(double[] data, int numberOfRows) throws Exception {
        if (data.length % numberOfRows != 0) {
            throw new Exception("size of data is not divisible by number of rows");
        }
        Matrix drawingMatrix = new Matrix(numberOfRows, data.length / numberOfRows);
        int i = 0;
        for (int row = 0; row < drawingMatrix.data.length; row++) {
            for (int column = 0; column < drawingMatrix.data[0].length; column++) {
                drawingMatrix.data[row][column] = data[i++];
            }
        }
        return drawingMatrix;
    }

    public Matrix scalorOperation(double x, ScalorOperation scalorOperation) {
        double[][] returnData = new double[data.length][data[0].length];
        IntStream.range(0, data.length).forEach(row -> IntStream.range(0, data[0].length).forEach(column -> {
            switch (scalorOperation) {
                case ADD:
                    returnData[row][column] = data[row][column] + x;
                    break;
                case SUBTRACT:
                    returnData[row][column] = data[row][column] - x;
                    break;
                case MULTIPLY:
                    returnData[row][column] = data[row][column] * x;
                    break;
                case DIVIDE:
                    returnData[row][column] = data[row][column] / x;
                    break;
            }
        }));
        return new Matrix(returnData);
    }

    public Matrix add(Matrix matrix) throws Exception {
        if ((data.length != matrix.data.length) || (data[0].length != matrix.data[0].length)) {
            throw new Exception("Matrices muset have matching size");
        }
        double[][] returnData = new double[data.length][data[0].length];
        IntStream.range(0, data.length).forEach(row -> IntStream.range(0, data[0].length)
                .forEach(column -> returnData[row][column] = data[row][column] + matrix.data[row][column]));
        return new Matrix(returnData);
    }

    public Matrix subtract(Matrix matrix) throws Exception {
        return add(matrix.scalorOperation(-1, ScalorOperation.MULTIPLY));
    }

    public Matrix multiply(Matrix matrix) throws Exception {
        if (data[0].length != matrix.data.length) {
            throw new Exception("Matrices must have matching inner dimensions");
        }
        double returnData[][] = new double[data.length][matrix.data[0].length];
        IntStream.range(0, data.length).forEach(row -> IntStream.range(0, matrix.data[0].length).forEach(column -> {
            double result = 0;
            for (int i = 0; i < data[0].length; i++) {
                result += data[row][i] * matrix.data[i][column];
                returnData[row][column] = result;
            }
        }));
        return new Matrix(returnData);
    }

    public static Matrix identity(int size) {
        Matrix matrix = new Matrix(size, size);
        IntStream.range(0, size).forEach(i -> matrix.data[i][i] = 1);
        return matrix;
    }

    public Matrix transpose() {
        double[][] returnData = new double[data[0].length][data.length];
        IntStream.range(0, data.length).forEach(row -> IntStream.range(0, data[0].length)
                .forEach(column -> returnData[column][row] = data[row][column]));
        return new Matrix(returnData);
    }

    public double dotProduct(Matrix matrix) throws Exception {
        if (!this.isVector() || !matrix.isVector()) {
            throw new Exception("can only dot product 2 vectors");
        } else if ((this.flatten().length != matrix.flatten().length)) {
            throw new Exception("Both vectors must have same size");
        }
        double returnValue = 0;
        for (int i = 0; i < this.flatten().length; i++) {
            returnValue += this.flatten()[i] * matrix.flatten()[i];
        }
        return returnValue;
    }

    public Matrix clear() {
        IntStream.range(0, data.length)
                .forEach(row -> IntStream.range(0, data[0].length).forEach(column -> data[row][column] = 0));
        return this;
    }

    public static Matrix toRowMatrix(double[] array) {
        double[][] data = new double[1][array.length];
        System.arraycopy(array, 0, data[0], 0, array.length);
        return new Matrix(data);
    }

    public Matrix getColumnMatrix(int column) {
        double[][] data = new double[this.data.length][1];
        IntStream.range(0, this.data.length).forEach(row -> data[row][0] = this.data[row][column]);
        return new Matrix(data);
    }

    public boolean isVector() {
        boolean flag = false;
        if (this.data.length == 1) {
            flag = true;
        } else if (this.data[0].length == 1) {
            flag = true;
        }
        return flag;
    }

    public double[] flatten() {
        double[] returnValue = new double[data.length * data[0].length];
        int i = 0;
        for (int row = 0; row < data.length; row++) {
            for (int column = 0; column < data[0].length; column++) {
                returnValue[i++] = data[row][column];
            }
        }
        return returnValue;
    }

    public String toString(String columnLabel, String rowLabel) {
        StringBuffer headingSB = new StringBuffer();
        headingSB.append("     |");
        IntStream.range(0, data[0].length)
                .forEach(i -> headingSB.append(" " + columnLabel + String.format("%02d", i) + ""));
        headingSB.append("\n");
        StringBuffer bodySb = new StringBuffer();
        IntStream.range(0, headingSB.length()).forEach(i -> bodySb.append("-"));
        bodySb.append("\n");
        IntStream.range(0, data.length).forEach(row -> {
            bodySb.append(rowLabel + String.format("%02d", row) + " |");
            IntStream.range(0, data[0].length).forEach(column -> {
                if (data[row][column] >= 0) {
                    bodySb.append("    " + (int) data[row][column]);
                } else {
                    bodySb.append("  " + (int) data[row][column]);
                }
            });
            bodySb.append("\n");
        });
        return headingSB.toString() + bodySb.toString();
    }

    public String toString() {
        return toString("C", "R");
    }

    public String toPackedString() {
        StringBuffer bodySB = new StringBuffer();
        IntStream.range(0, data.length).forEach(
                row -> IntStream.range(0, data[0].length).forEach(column -> bodySB.append((int) data[row][column])));
        return bodySB.toString();
    }
}
