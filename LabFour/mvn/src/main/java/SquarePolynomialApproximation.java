import java.io.PrintStream;
import java.util.ArrayList;
import Jama.Matrix;


public class SquarePolynomialApproximation {

    public static double applyApproximation(ArrayList<Double> X, ArrayList<Double> Y, PrintStream out) {
        double SX = SA(X);
        double SX2 = SAB(X, X);
        double SX3 = SABC(X, X, X);
        double SX4 = SABCD(X, X, X, X);
        double SY = SA(Y);
        double SXY = SAB(X, Y);
        double SX2Y = SABC(X, X, Y);
        int n = X.size();

        double[][] lhsArray = {{n, SX, SX2}, {SX, SX2, SX3}, {SX2, SX3, SX4}};
        double[] rhsArray = {SY, SXY, SX2Y};
        Matrix lhs = new Matrix(lhsArray);
        Matrix rhs = new Matrix(rhsArray, 3);

        Matrix ans = lhs.solve(rhs);

        double a0 = ans.get(0, 0);
        double a1 = ans.get(1, 0);
        double a2 = ans.get(2, 0);

        out.printf("Approximate function: %.4fx^2 + %.4fx + %.4f\n", a2, a1, a0);
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
            out.printf("%.4f ", (x * x * a2 + x * a1 + a0));
        }
        out.println();
        out.println("e:");
        double S = 0;
        double[] x = new double[X.size()];
        double[] y = new double[X.size()];
        double[] f = new double[X.size()];
        for (int i = 0; i < n; i++) {
            out.printf("%.4f ", ((X.get(i) * X.get(i) * a2 + X.get(i) * a1 + a0) - Y.get(i)));
            S += ((X.get(i) * X.get(i) * a2 + X.get(i) * a1 + a0) - Y.get(i))*((X.get(i) * X.get(i) * a2 + X.get(i) * a1 + a0) - Y.get(i));
            x[i] = X.get(i);
            y[i] = Y.get(i);
            f[i] = (X.get(i) * X.get(i) * a2 + X.get(i) * a1 + a0);
        }
        out.println();
        out.println("S = " + S);
        out.println("delta = " + Math.sqrt(S/n));
        Function.drawPlot(x, y, f, "Square Polynomial Approximation");
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

    private static double SABC(ArrayList<Double> A, ArrayList<Double> B, ArrayList<Double> C) {
        double SABC = 0;
        for (int i = 0; i < A.size(); i++) {
            SABC += A.get(i) * B.get(i) * C.get(i);
        }
        return SABC;
    }

    private static double SABCD(ArrayList<Double> A, ArrayList<Double> B, ArrayList<Double> C, ArrayList<Double> D) {
        double SABCD = 0;
        for (int i = 0; i < A.size(); i++) {
            SABCD += A.get(i) * B.get(i) * C.get(i) * D.get(i);
        }
        return SABCD;
    }

}
