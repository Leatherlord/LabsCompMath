import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TrapezeMethod implements IMethod {

    public void applyMethod(Method function, double left, double right, double accuracy) throws InvocationTargetException, IllegalAccessException {
        int n = 4;
        double step = (right - left) / n;
        int iterations = 1;
        double I_0 = cycle(function, left, step, n);
        n *= 2;
        step /= 2;
        double I_1 = cycle(function, left, step, n);
        System.out.println("FOR TRAPEZE METHOD:\n");
//        while (Math.abs(I_0 - I_1)/3 > accuracy) {
        while (Math.abs(I_0 - I_1) > accuracy) {
            I_0 = I_1;
            n *= 2;
            step /= 2;
            I_1 = cycle(function, left, step, n);
            iterations++;
            if (iterations > 10) {
                System.out.println("Iterations > 10 - probably we cannot approach any further");
                break;
            }
        }
        System.out.printf("""
                Result: %f
                Iterations: %d
                Split in: %d sections (n)
                Step size: %f
                
                """, I_1, iterations, n, step);
    }

    private double cycle(Method function, double left, double step, int n) throws InvocationTargetException, IllegalAccessException {
        double I_0 = 0;
        for (int i = 0; i < n; i++) {
            I_0 += ((Double) function.invoke(null, left) + (Double) function.invoke(null, left + step)) / 2 * step;
            left += step;
        }
        return I_0;
    }
}
