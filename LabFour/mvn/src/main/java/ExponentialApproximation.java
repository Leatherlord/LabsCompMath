import java.io.PrintStream;
import java.util.ArrayList;

public class ExponentialApproximation {

    public static double applyApproximation(ArrayList<Double> X_start, ArrayList<Double> Y_start, PrintStream out) {
        ArrayList<Double> X = new ArrayList<>();
        ArrayList<Double> Y = new ArrayList<>();
        for (int i = 0; i < X_start.size(); i++) {
            X.add(X_start.get(i));
            Y.add(Math.log(Y_start.get(i)));
        }

        double SX = SA(X);
        double SXX = SAB(X, X);
        double SY = SA(Y);
        double SXY = SAB(X, Y);
        int n = X.size();

        double delta = SXX * n - SX * SX;
        double delta1 = SXY * n - SX * SY;
        double delta2 = SXX * SY - SX * SXY;

        double A = delta1/delta;
        double b = delta2/delta;

        double a = Math.exp(b);
        b = A;

        out.printf("Approximate function: %.4fe^(%.4fx)\n", a, b);
        out.println("X:");
        for (double x : X_start) {
            out.print(x + " ");
        }
        out.println();
        out.println("Y:");
        for (double y : Y_start) {
            out.print(y + " ");
        }
        out.println();
        out.println("Y approx:");
        for (double x : X_start) {
            out.printf("%.4f ", (a * Math.exp(x * b)));
        }
        out.println();
        out.println("e:");
        double S = 0;
        double[] x = new double[X.size()];
        double[] y = new double[X.size()];
        double[] f = new double[X.size()];
        for (int i = 0; i < n; i++) {
            out.printf("%.4f ", ((Math.exp(X_start.get(i) * b) * a) - Y_start.get(i)));
            S += ((Math.exp(X_start.get(i) * b) * a) - Y_start.get(i))*((Math.exp(X_start.get(i) * b) * a) - Y_start.get(i));
            x[i] = X_start.get(i);
            y[i] = Y_start.get(i);
            f[i] = (Math.exp(X_start.get(i) * b) * a);
        }
        out.println();
        out.println("S = " + S);
        out.println("delta = " + Math.sqrt(S/n));
        Function.drawPlot(x, y, f, "Exponential Approximation");
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
}
