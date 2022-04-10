import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("File (f) of console (c)?");
        String answer;
        PrintStream out = null;
        while (true) {
            answer = scanner.nextLine();
            if (answer.equals("f")) {
                try {
                    out = new PrintStream("output.txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
            if (answer.equals("c")) {
                out = System.out;
                break;
            }
            System.out.println("File (f) or console (c)?");
        }
        if (answer.equals("f")) {
            try {
                scanner = new Scanner(new File("input.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        String xStr = scanner.nextLine();
        String[] xArr = xStr.split(" ");
        ArrayList<Double> X = (ArrayList<Double>) Arrays.stream(xArr).map(Double::parseDouble).collect(Collectors.toList());
        String yStr = scanner.nextLine();
        String[] yArr = yStr.split(" ");
        ArrayList<Double> Y = (ArrayList<Double>) Arrays.stream(yArr).map(Double::parseDouble).collect(Collectors.toList());

        String winner = "LINEAR APPROXIMATION";
        out.println("""
                ===============================================
                LINEAR APPROXIMATION
                ===============================================""");
        double delta = LinearApproximation.applyApproximation(X, Y, out);
        out.println("""
                ===============================================
                SQUARE POLYNOMIAL APPROXIMATION
                ===============================================""");
        double val = SquarePolynomialApproximation.applyApproximation(X, Y, out);
        if (val < delta) {
            delta = val;
            winner = "SQUARE POLYNOMIAL APPROXIMATION";
        }
        out.println("""
                ===============================================
                POWER FUNCTION APPROXIMATION
                ===============================================""");
        val = PowerFunctionApproximation.applyApproximation(X, Y, out);
        if (val < delta) {
            delta = val;
            winner = "POWER FUNCTION APPROXIMATION";
        }
        out.println("""
                ===============================================
                EXPONENTIAL APPROXIMATION
                ===============================================""");
        val = ExponentialApproximation.applyApproximation(X, Y, out);
        if (val < delta) {
            delta = val;
            winner = "EXPONENTIAL APPROXIMATION";
        }
        out.println("""
                ===============================================
                LOGARITHMIC APPROXIMATION
                ===============================================""");
        val = LogarithmicApproximation.applyApproximation(X, Y, out);
        if (val < delta) {
            delta = val;
            winner = "LOGARITHMIC APPROXIMATION";
        }
        out.println("""
                ===============================================
                CUBE POLYNOMIAL APPROXIMATION
                ===============================================""");
        val = CubePolynomialApproximation.applyApproximation(X, Y, out);
        if (val < delta) {
            delta = val;
            winner = "CUBE POLYNOMIAL APPROXIMATION";
        }
        out.printf("""
                ===============================================
                %s IS THE BEST ONE
                WITH DELTA = %.4f
                ===============================================""", winner, delta);

    }

}
