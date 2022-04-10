import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RectanglesMethod implements IMethod {

    public void applyMethod(Method function, double left, double right, double accuracy) throws InvocationTargetException, IllegalAccessException {
        int n = 4;
        double step = (right - left) / n;
        System.out.println("FOR RECTANGLES METHOD:\n");
        System.out.println("Left boundary:");
        apply(function, left, step, accuracy, n, 0);
        System.out.println("Right boundary:");
        apply(function, left + step, step, accuracy, n, 1);
        System.out.println("Middle point:");
        apply(function, left + step/2, step, accuracy, n, 0.5);
    }

    private void apply(Method function, double point, double step, double accuracy, int n, double coefficient) throws InvocationTargetException, IllegalAccessException {
        int iterations = 1;
        double I_0 = cycle(function, point, step, n);
        n *= 2;
        step /= 2;
        point -= step * coefficient;
        double I_1 = cycle(function, point, step, n);
//        while (Math.abs(I_0 - I_1)/3 > accuracy) {
        while (Math.abs(I_0 - I_1) > accuracy) {
            I_0 = I_1;
            n *= 2;
            step /= 2;
            point -= step * coefficient;
            I_1 = cycle(function, point, step, n);
            iterations++;
            if (iterations > 1000) {
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

    private double cycle(Method function, double point, double step, int n) throws InvocationTargetException, IllegalAccessException {
        double I_0 = 0;
        for (int i = 0; i < n; i++) {
            I_0 += (Double) function.invoke(null, point) * step;
            point += step;
        }
        return I_0;
    }
}
