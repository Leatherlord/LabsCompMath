import java.util.ArrayList;

public class SimpleIterator {

    public static void research(Matrix matrix, double[] freeCoefficients, double accuracy) {
        doDiagonalDominance(matrix, freeCoefficients);
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                System.out.print(matrix.get(i, j) + " ");
            }
            System.out.println();
        }
        transformMatrix(matrix, freeCoefficients);
        iterate(matrix, freeCoefficients, accuracy);
    }

    private static void iterate(Matrix matrix, double[] xi, double accuracy) {
        int iterations = 0;
        ArrayList<Double> errors = new ArrayList<>();
        double currError = accuracy;
        double[] newValues;

        while (currError >= accuracy) {
            currError = 0;
            iterations++;
            newValues = new double[xi.length];
            for (int i = 0; i < matrix.getRows(); i++) {
                newValues[i] = 0;
                for (int j = 0; j < matrix.getColumns(); j++) {
                    if (j == matrix.getColumns() - 1) {
                        newValues[i] += matrix.get(i, j);
                        break;
                    }
                    if (j < i) {
                        newValues[i] += matrix.get(i, j) * xi[j];
                    } else {
                        newValues[i] += matrix.get(i, j) * xi[j + 1];
                    }
                }
            }
            for (int i = 0; i < newValues.length; i++) {
                currError = Math.max(Math.abs(newValues[i] - xi[i]), currError);
            }
            errors.add(currError);
            xi = newValues;
            if (iterations > 1000) {
                System.out.println("Too many iterations (>1000)");
                System.exit(1);
            }

        }
        System.out.print("X: ");
        for (double x : xi) {
            System.out.printf("%.3f ", x);
        }
        System.out.print("\n");
        System.out.println("Iterations: " + iterations);
        System.out.print("Errors: ");
        for (double error : errors) {
            System.out.printf("%1.4f ", error);
        }
        System.out.print("\n");

    }

    private static void transformMatrix(Matrix newMatrix, double[] freeCoefficients) {
        double[] xC = new double[newMatrix.getColumns()];
        for (int i = 0; i < newMatrix.getRows(); i++) {
            xC[i] = newMatrix.get(i, i);
            freeCoefficients[i] /= xC[i];
        }
        for (int i = 0; i < newMatrix.getRows(); i++) {
            for (int j = 0; j < newMatrix.getColumns() - 1; j++) {
                if (j < i) {
                    newMatrix.set(i, j, -newMatrix.get(i, j) / xC[i]);
                } else {
                    newMatrix.set(i, j, -newMatrix.get(i, j + 1) / xC[i]);
                }
            }
            newMatrix.set(i, newMatrix.getColumns() - 1, freeCoefficients[i]);
        }
    }

    private static void doDiagonalDominance(Matrix matrix, double[] freeCoefficients) {
        for (int i = 0; i < matrix.getRows(); i++) {
            int sum = 0;
            for (int j = 0; j < matrix.getColumns(); j++) {
                if (j == i) continue;
                sum += Math.abs(matrix.get(i, j));
            }
            if (Math.abs(matrix.get(i, i)) >= sum) continue;
            boolean done = false;
            for (int j = i + 1; j < matrix.getRows(); j++) {
                int newSum = 0;
                for (int k = 0; k < matrix.getColumns(); k++) {
                    if (k == i) continue;
                    newSum += Math.abs(matrix.get(j, k));
                }
                if (Math.abs(matrix.get(j, i)) >= newSum) {
                    ArrayList<Double> betweener = matrix.getRow(i);
                    double cBetweener = freeCoefficients[i];
                    matrix.setRow(matrix.getRow(j), i);
                    freeCoefficients[i] = freeCoefficients[j];
                    matrix.setRow(betweener, j);
                    freeCoefficients[j] = cBetweener;
                    done = true;
                    break;
                }
            }
            if (!done) {
                System.out.println("Dominance is impossible to establish");
                System.exit(1);
            }
        }
    }
}
