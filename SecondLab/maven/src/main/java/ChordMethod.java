import Functions.Functions;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ChordMethod {

    public static void applyChordMethod(double a, double b, int functionNumber, double accuracy, PrintStream out) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        double prevApproxX;
        double approxX;
        double startingA = a;
        double startingB = b;

        Method function = null;
        switch (functionNumber) {
            case 1:
                function = Functions.class.getDeclaredMethod("applyFunctionOne", double.class);
                break;
            case 2:
                function = Functions.class.getDeclaredMethod("applyFunctionTwo", double.class);
                break;
            case 3:
                function = Functions.class.getDeclaredMethod("applyFunctionThree", double.class);
                break;
            default:
                out.println("Unknown function - try numbers 1-3");
                return;
        }
        if ((((Double) function.invoke(null, a) > 0) == ((Double) function.invoke(null, b) > 0)) ||
                (((Double) function.invoke(null, a) < 0) == ((Double) function.invoke(null, b) < 0))) {
            out.println("Chord method is not applicable if ends of an interval are both positive or both negative");
            return;
        }
        prevApproxX = a - (b - a) / ((Double) function.invoke(null, b) - (Double) function.invoke(null, a)) * (Double) function.invoke(null, a);
        approxX = prevApproxX + accuracy + 0.00000001;
        int iterations = 0;
        if (Math.abs((Double) function.invoke(null, prevApproxX)) <= accuracy) {
            iterations = 1;
            approxX = prevApproxX;
        } else {
            iterations = 0;
            while ((Math.abs(approxX - prevApproxX) >= accuracy) || Math.abs((Double) function.invoke(null, approxX)) >= accuracy) {
                if (iterations > 1000) {
                    out.println("It's more than 1000 iterations past - seems like we cannot find solution");
                    return;
                }
                if ((((Double) function.invoke(null, a) > 0) == ((Double) function.invoke(null, approxX) < 0)) ||
                        (((Double) function.invoke(null, a) < 0) == ((Double) function.invoke(null, approxX) > 0))) {
                    b = approxX;
                } else {
                    a = approxX;
                }
                prevApproxX = approxX;
                approxX = a - (b - a) / ((Double) function.invoke(null, b) - (Double) function.invoke(null, a)) * (Double) function.invoke(null, a);
                iterations++;
            }
        }
        out.printf("""
                For function you have chosen for chord method within an interval (%.3f; %.3f):
                With accuracy taken as: %f
                Approximation of X: %.3f
                f(x): %.3f
                Iterations: %d
                                
                """, startingA, startingB, accuracy, approxX, function.invoke(null, approxX), iterations);
        Functions.drawPlot(startingA, startingB, function);
    }

}
