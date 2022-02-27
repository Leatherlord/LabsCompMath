import Functions.Functions;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SimpleIterationMethod {

    public static void applySimpleIterationMethod(double a, double b, int functionNumber, double accuracy, PrintStream out) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        double prevApproxX;
        double approxX;
        Method function = null;
        Method phi = null;
        Method phiDiff = null;
        switch (functionNumber) {
            case 1:
                function = Functions.class.getDeclaredMethod("applyFunctionOne", double.class);
                phi = Functions.class.getDeclaredMethod("applyPhiOne", double.class);
                phiDiff = Functions.class.getDeclaredMethod("applyPhiOneDiff", double.class);
                break;
            case 2:
                function = Functions.class.getDeclaredMethod("applyFunctionTwo", double.class);
                phi = Functions.class.getDeclaredMethod("applyPhiTwo", double.class);
                phiDiff = Functions.class.getDeclaredMethod("applyPhiTwoDiff", double.class);
                break;
            case 3:
                function = Functions.class.getDeclaredMethod("applyFunctionThree", double.class);
                phi = Functions.class.getDeclaredMethod("applyPhiThree", double.class);
                phiDiff = Functions.class.getDeclaredMethod("applyPhiThreeDiff", double.class);
                break;
            default:
                out.println("Unknown function - try numbers 1-3");
                return;
        }
        if (Math.abs((Double) phiDiff.invoke(null, a)) >= 1 ||
                Math.abs((Double) phiDiff.invoke(null, b)) >= 1) {
            out.println("Sufficient Condition for Convergence is broken with this interval");
            return;
        }
        double i = a;
        double q = (Double) phiDiff.invoke(null, a);
        while (i < b) {
            i += 0.0000001;
            if ((Double) phiDiff.invoke(null, i) >= 1) {
                out.println("Sufficient Condition for Convergence is broken with this interval");
                return;
            }
            q = Math.max(q, (Double) phiDiff.invoke(null, i));
        }
        prevApproxX = a;
        approxX = (Double) phi.invoke(null, a);
        int iterations = 0;
        while ((q <= 0.5 && Math.abs(approxX - prevApproxX) > accuracy) ||
                (Math.abs(approxX - prevApproxX) > (1 - q) * accuracy / q)) {
            if (iterations > 1000) {
                out.println("It's more than 1000 iterations past - seems like we cannot find solution");
                return;
            }
            prevApproxX = approxX;
            approxX = (Double) phi.invoke(null, approxX);
            iterations++;
        }
        out.printf("""
                For function you have chosen for simple iteration method within an interval (%.3f; %.3f):
                With accuracy taken as: %f
                Approximation of X: %.3f
                f(x): %.3f
                Iterations: %d
                                
                """, a, b, accuracy, approxX, function.invoke(null, approxX), iterations);
        Functions.drawPlot(a, b, function);
    }

}
