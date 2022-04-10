import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;
import java.util.ArrayList;

public class SimpleIterationForSystemMethod {

    // 0.1x^2 + x + 0.2y^2 - 0.3 = 0
    // 0.2x^2 + y - 0.1xy - 0.7 = 0
    // 0 < x < 1
    // 0 < y < 1
    public static void applySimpleIterationForSystem(double accuracy, PrintStream out) {

        for (double x = 0; x < 1; x += 0.001) {
            for (double y = 0; y < 1; y += 0.001) {
                if (applyPhi1DiffAbs(x, y) >= 1 || applyPhi2DiffAbs(x, y) >= 1) {
                    out.println("Sufficient Condition for Convergence is broken");
                    return;
                }
            }
        }

        double prevY = 0.2;
        double prevX = 0.6;
        double y = applyPhi2(prevX, prevY);
        double x = applyPhi1(prevX, prevY);

        int iterations = 0;

        ArrayList<Double> xErrors = new ArrayList<>();
        ArrayList<Double> yErrors = new ArrayList<>();


        while (
                Math.max(Math.abs(x - prevX), Math.abs(y - prevY)) > accuracy
//                        ||
//                Math.max(Math.abs(applyFunctionOne(x, y)), Math.abs(applyFunctionTwo(x,y))) > accuracy
        ) {
            if (iterations > 10000) {
                out.println("It's more than 1000 iterations past - seems like we cannot find solution");
                return;
            }
            prevY = y;
            prevX = x;
            y = applyPhi2(prevX, prevY);
            x = applyPhi1(prevX, prevY);
            System.out.println(x + " " + y + " " + applyFunctionOne(x, y) + " " + applyFunctionTwo(x,y));
            xErrors.add(Math.abs(x - prevX));
            yErrors.add(Math.abs(y - prevY));
            iterations++;
//            if (Math.abs(applyFunctionOne(x, y)) < accuracy && Math.abs(applyFunctionTwo(x,y)) < accuracy) {
//                break;
//            }
        }
        out.printf("""
                For system of two equations for simple iteration method:
                1) 0.1x^2 + x + 0.2y^2 - 0.3 = 0
                2) 0.2x^2 + y - 0.1xy - 0.7 = 0
                With accuracy taken as: %f
                Approximation for X: %f
                Approximation for Y: %f
                f1(x, y): %f
                f2(x, y): %f
                Iterations: %d
                
                                
                """, accuracy, x, y, applyFunctionOne(x, y), applyFunctionTwo(x, y), iterations);
        out.println("Errors for X:");
        for (double error : xErrors) {
            out.printf("%.3f ", error);
        }
        out.println("\nErrors for Y:");
        for (double error : yErrors) {
            out.printf("%.3f ", error);
        }
        out.println();

        JFrame frame = new JFrame("Plot");
        ImageIcon icon = new ImageIcon("systemPlot.png");
        Image image = icon.getImage();
        frame.add(new MyPanel(image));
        frame.setBounds(200, 200, 940, 500);
        frame.setVisible(true);
    }

    private static double applyFunctionOne(double x, double y) {
        return 0.1*Math.pow(x,2) + x + 0.2*Math.pow(y, 2) - 0.3;
    }

    private static double applyFunctionTwo(double x, double y) {
        return 0.2*Math.pow(x,2) + y - 0.1*x*y - 0.7;
    }

    private static double applyPhi1(double x, double y) {
        return 0.3 - 0.1 * Math.pow(x, 2) - 0.2 * Math.pow(y, 2);
    }

    private static double applyPhi2(double x, double y) {
        return 0.7 - 0.2 * Math.pow(x, 2) - 0.1 * x * y;
    }

    private static double applyPhi1DiffAbs(double x, double y) {
        return Math.abs(-0.2 * x) + Math.abs(-0.4 * y);
    }

    private static double applyPhi2DiffAbs(double x, double y) {
        return Math.abs(-0.4 * x + 0.1 * y) + Math.abs(0.1 * x);
    }

    private static class MyPanel extends JPanel {
        private final Image image;

        private MyPanel(Image image) {
            this.image = image;
        }

        public void paintComponent(Graphics g) {
            g.drawImage(image, 0, 0, null);
        }
    }

}
