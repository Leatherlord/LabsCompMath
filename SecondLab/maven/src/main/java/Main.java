import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
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
        double a;
        double b;
        double accuracy;
        int functionNumber;
        if (answer.equals("f")) {
            System.out.println("""
                    Choose function from the list:
                    1) x^3 - 1.89x^2 - 2x + 1.76
                    2) sin(x) + 0.1x^2 + x
                    3) 2cos^2(x)x + 0.2x^2 + x + 1.1
                                        
                    Create file named "input.txt" in this directory.
                    Fulfill it with arguments in this order:
                    function_number
                    left_bound_of_interval
                    right_bound_of_interval
                    accuracy
                                        
                    When you ready, type (y).
                    """);
            while (!scanner.nextLine().equals("y")) ;
            try {
                scanner = new Scanner(new File("input.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (answer.equals("c")) {
            System.out.println("""
                    Choose function from the list and type it's number below:
                    1) x^3 - 1.89x^2 - 2x + 1.76
                    2) sin(x) + 0.1x^2 + x
                    3) 2cos^2(x)x + 0.2x^2 + x + 1.1
                    """);
        }
        functionNumber = scanner.nextInt();
        if (answer.equals("c")) {
            System.out.println("""
                    Type left and right boundaries of an interval below:
                    """);
        }
        a = scanner.nextDouble();
        b = scanner.nextDouble();
        if (answer.equals("c")) {
            System.out.println("""
                    Type accuracy below:
                    """);
        }
        accuracy = scanner.nextDouble();

        ChordMethod.applyChordMethod(a, b, functionNumber, accuracy, out);
        SimpleIterationMethod.applySimpleIterationMethod(a, b, functionNumber, accuracy, out);
        SimpleIterationForSystemMethod.applySimpleIterationForSystem(accuracy, out);
    }
}
