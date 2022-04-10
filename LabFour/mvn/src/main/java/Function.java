import org.math.plot.Plot2DPanel;

import javax.swing.*;

public class Function {
    public static double func(double x) {
        return (4 * x)/(Math.pow(x, 4) + 1);
    }

    public static void drawPlot(double[] x, double[] y, double[] f, String message) {
        Plot2DPanel plot = new Plot2DPanel();
        plot.addLinePlot("Y", x, y);
        plot.addLinePlot("F(X)", x, f);
        JFrame frame = new JFrame(message);
        frame.setBounds(200, 200, 600, 600);
        frame.setContentPane(plot);
        frame.setVisible(true);
    }
}
