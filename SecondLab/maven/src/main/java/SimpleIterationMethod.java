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
        Method functionDiff = null;
        switch (functionNumber) {
            case 1:
                function = Functions.class.getDeclaredMethod("applyFunctionOne", double.class);
                phi = Functions.class.getDeclaredMethod("applyPhiOne", double.class);
                phiDiff = Functions.class.getDeclaredMethod("applyPhiOneDiff", double.class);
                functionDiff = Functions.class.getDeclaredMethod("applyFunctionOneDiff", double.class);
                break;
            case 2:
                function = Functions.class.getDeclaredMethod("applyFunctionTwo", double.class);
                phi = Functions.class.getDeclaredMethod("applyPhiTwo", double.class);
                phiDiff = Functions.class.getDeclaredMethod("applyPhiTwoDiff", double.class);
                functionDiff = Functions.class.getDeclaredMethod("applyFunctionTwoDiff", double.class);

                break;
            case 3:
                function = Functions.class.getDeclaredMethod("applyFunctionThree", double.class);
                phi = Functions.class.getDeclaredMethod("applyPhiThree", double.class);
                phiDiff = Functions.class.getDeclaredMethod("applyPhiThreeDiff", double.class);
                functionDiff = Functions.class.getDeclaredMethod("applyFunctionThreeDiff", double.class);

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
//        double q = (Double) phiDiff.invoke(null, a);
        double max = Math.abs((Double) functionDiff.invoke(null, i));

        while (i < b) {
            i += 0.0000001;
            if ((Double) phiDiff.invoke(null, i) >= 1) {
//                out.println("Sufficient Condition for Convergence is broken with this interval");
//                return;
            }
//            q = Math.max(q, (Double) phiDiff.invoke(null, i));
            max = Math.max(max, Math.abs((Double)functionDiff.invoke(null, i)));
        }

        System.out.println(functionDiff.invoke(null, a) + " " + functionDiff.invoke(null, b) + " " + max);
        prevApproxX = b;
        double lambda = -1/max;
        System.out.println(newPhiDiff(a, lambda, functionDiff) + " " + newPhiDiff(b, lambda, functionDiff));
        approxX = newPhi(prevApproxX, lambda, function);
        int iterations = 0;
        while ((
//                q <= 0.5 &&
//                Math.abs(approxX - prevApproxX) > accuracy)
//                ||
//                (Math.abs(approxX - prevApproxX) > (1 - q) * accuracy / q)
                Math.abs((Double)function.invoke(null, approxX)) > accuracy)
        ) {
            if (iterations > 1000) {
                out.println("It's more than 1000 iterations past - seems like we cannot find solution");
                return;
            }
            prevApproxX = approxX;
            approxX = newPhi(approxX, lambda, function);
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

    private static double applyFunctionOneDiff(double x) {
        return 3*Math.pow(x, 2) - 1.89*2*x - 2;
    }

    private static double applyFunctionOne(double x) {
        return Math.pow(x, 3) - 1.89*Math.pow(x,2) - 2*x + 1.76;
    }

    private static double newPhi(double x, double lambda, Method function) throws InvocationTargetException, IllegalAccessException {
        return x + lambda * (Double) function.invoke(null, x);
    }

    private static double newPhiDiff(double x, double lambda, Method functionDiff) throws InvocationTargetException, IllegalAccessException {
        return 1 + lambda* (Double) functionDiff.invoke(null,x);
    }

}
