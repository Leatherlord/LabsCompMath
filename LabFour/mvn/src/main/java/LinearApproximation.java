import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.io.PrintStream;
import java.util.ArrayList;

public class LinearApproximation {

    public static double applyApproximation(ArrayList<Double> X, ArrayList<Double> Y, PrintStream out) {
        double SX = SA(X);
        double SXX = SAB(X, X);
        double SY = SA(Y);
        double SXY = SAB(X, Y);
        int n = X.size();

        double delta = SXX * n - SX * SX;
        double delta1 = SXY * n - SX * SY;
        double delta2 = SXX * SY - SX * SXY;

        double a = delta1/delta;
        double b = delta2/delta;

        out.printf("Approximate function: %.4fx + %.4f\n", a, b);
        out.println("X:");
        for (double x : X) {
            out.print(x + " ");
        }
        out.println();
        out.println("Y:");
        for (double y : Y) {
            out.print(y + " ");
        }
        out.println();
        out.println("Y approx:");
        for (double x : X) {
            out.printf("%.4f ", (x * a + b));
        }
        out.println();
        out.println("e:");
        double S = 0;
        double[] x = new double[X.size()];
        double[] y = new double[X.size()];
        double[] f = new double[X.size()];
        for (int i = 0; i < n; i++) {
            out.printf("%.4f ", ((X.get(i) * a + b) - Y.get(i)));
            S += ((X.get(i) * a + b) - Y.get(i))*((X.get(i) * a + b) - Y.get(i));
            x[i] = X.get(i);
            y[i] = Y.get(i);
            f[i] = (X.get(i) * a + b);
        }
        out.println();
        out.println("S = " + S);
        out.println("delta = " + Math.sqrt(S/n));
        out.println("Pearson's coefficient: " + pearsonC(X, Y));
        Function.drawPlot(x, y, f, "Linear Approximation");
        return Math.sqrt(S/n);
    }

    private static double SA(ArrayList<Double> A) {
        double SA = 0;
        for (Double a : A) {
            SA += a;
        }
        return SA;
    }

    private static double SAB(ArrayList<Double> A, ArrayList<Double> B) {
        double SAB = 0;
        for (int i = 0; i < A.size(); i++) {
            SAB += A.get(i) * B.get(i);
        }
        return SAB;
    }

    private static double pearsonC(ArrayList<Double> X, ArrayList<Double> Y) {
        double xAvg = SA(X)/X.size();
        double yAvg = SA(Y)/Y.size();
        double topSum = 0;
        double botSum1 = 0;
        double botSum2 = 0;
        for (int i = 0; i < X.size(); i++) {
            topSum += (X.get(i) - xAvg) * (Y.get(i) - yAvg);
            botSum1 += (X.get(i) - xAvg) * (X.get(i) - xAvg);
            botSum2 += (Y.get(i) - yAvg) * (Y.get(i) - yAvg);
        }
        return topSum/Math.sqrt(botSum1 * botSum2);
    }


}
