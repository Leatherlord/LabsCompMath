package Functions;

import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Functions {

    public static void drawPlot(double a, double b, Method function) throws InvocationTargetException, IllegalAccessException {
        double i = (int) ((a - 1) * 10000);
        double[] x = new double[(int) ((b - a + 2) * 10000)];
        double[] y = new double[(int) ((b - a + 2) * 10000)];
        while (i < x.length + (int) ((a - 1) * 10000)) {
            x[(int) i - (int) ((a - 1) * 10000)] = i / 10000;
            y[(int) i - (int) ((a - 1) * 10000)] = (Double) function.invoke(null, i / 10000);
            i++;
        }
        Plot2DPanel plot = new Plot2DPanel();
        plot.addLinePlot("My plot", x, y);
        JFrame frame = new JFrame("Plot");
        frame.setBounds(200, 200, 600, 600);
        frame.setContentPane(plot);
        frame.setVisible(true);
    }

    public static double applyFunctionOne(double a) { // x^3 - 1.89x^2 - 2x + 1.76
        return Math.pow(a, 3) - 1.89 * Math.pow(a, 2) - 2 * a + 1.76;
    }

    public static double applyPhiOne(double a) {
        return (Math.pow(a, 3) - 1.89 * Math.pow(a, 2) + 1.76) / 2;
    }

    public static double applyPhiOneDiff(double a) {
        return (3 * Math.pow(a, 2) - 1.89 * 2 * a) / 2;
    }

    public static double applyFunctionTwo(double a) { // sin(x) + 0.1x^2 + x
        return Math.sin(a) + Math.pow(a, 2) * 0.1 + a;
    }

    public static double applyPhiTwo(double a) {
        return -(Math.sin(a) + Math.pow(a, 2) * 0.1);
    }

    public static double applyPhiTwoDiff(double a) {
        return -(Math.cos(a) + 0.2 * a);
    }

    public static double applyFunctionThree(double a) { // 2cos^2(x)x + 0.2x^2 + x + 1.1
        return 2 * Math.pow(Math.cos(a), 2) * a + 0.2 * Math.pow(a, 2) + a + 1.1;
    }

    public static double applyPhiThree(double a) {
        return -(2 * Math.pow(Math.cos(a), 2) * a + 0.2 * Math.pow(a, 2) + 1.1);
    }

    public static double applyPhiThreeDiff(double a) {
        return -0.4 * a - 2 * Math.pow(Math.cos(a), 2) + 4 * Math.sin(a) * Math.cos(a);
    }


}
