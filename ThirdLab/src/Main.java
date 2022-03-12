import Functions.Functions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Choose function:
                1) -2x^3 - 4x^2 + 8x - 4
                2) sin(x)x
                3) (asin(|(x^6)/8|)^sqrt(|0.4x|)   (Only exists on interval [-1.4 - ε; 1.4 + ε])
                """);
        Method function = null;
        switch (scanner.nextInt()) {
            case 1 -> function = Functions.class.getMethod("functionOne", double.class);
            case 2 -> function = Functions.class.getMethod("functionTwo", double.class);
            case 3 -> function = Functions.class.getMethod("functionThree", double.class);
            default -> {
                System.out.println("Wrong function. Terminating...");
                System.exit(1);
            }
        }
        System.out.println("Enter left and right boundaries:");
        double left = scanner.nextDouble();
        double right = scanner.nextDouble();
        if ((left < -1.4 || right > 1.4) && function.equals(Functions.class.getMethod("functionThree", double.class))) {
            System.out.println("This function does not exist on this interval");
            System.exit(1);
        }
        System.out.println("Enter accuracy:");
        double accuracy = scanner.nextDouble();
        System.out.println("""
                Choose method:
                1) Rectangles
                2) Trapeze
                """);
        IMethod method = null;
        switch (scanner.nextInt()) {
            case 1 -> method = new RectanglesMethod();
            case 2 -> method = new TrapezeMethod();
            default -> {
                System.out.println("Wrong method. Terminating...");
                System.exit(1);
            }
        }
        method.applyMethod(function, left, right, accuracy);

    }
}
