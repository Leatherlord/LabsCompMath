import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("File (f) or console (c):");

        Matrix A;
        double[] coeffs;

        String answer = scanner.nextLine();
        if (answer.equals("f")) {
            System.out.println("""
                    Create file named 'input.txt' and place it in the same directory where this jar located.
                    Input should look like this:
                    <how_many_dimensions>
                    <a_1_1> <a_1_2> <a_1_3> . . . <a_1_<how_many_dimensions>>
                    <a_2_1> <a_2_2> . . .
                    . . .
                    <a_<how_many_dimensions>_1> <a_<how_many_dimensions>_2> . . . <a_<how_many_dimensions>_<how_many_dimensions>>
                    <b1> (free coefficients)
                    <b2>
                    . . .
                    <b_<how_many_dimensions>>
                    <accuracy>
                    Then type in 'y' (for yes) when it's ready.""");
            while (!scanner.nextLine().equals("y")) {
                System.out.println("Type in 'y' if ready");
            }
            try {
                Scanner fileScanner = new Scanner(new File("input.txt"));
                int dimensions = Integer.parseInt(fileScanner.nextLine());
                A = new Matrix(dimensions, dimensions);
                coeffs = new double[dimensions];
                for (int i = 0; i < dimensions; i++) {
                    ArrayList<Double> line = (ArrayList<Double>) Arrays.stream(fileScanner.nextLine().split(" ")).map(Double::parseDouble).collect(Collectors.toList());
                    for (int j = 0; j < dimensions; j++) {
                        A.set(i, j, line.get(j));
                    }
                }
                for (int i = 0; i < dimensions; i++) {
                    coeffs[i] = Double.parseDouble(fileScanner.nextLine());
                }
                double acc = Double.parseDouble(fileScanner.nextLine());
                SimpleIterator.research(A, coeffs, acc);
            } catch (FileNotFoundException e) {
                System.out.println("No input.txt found");
                System.exit(1);
            }

        } else if (answer.equals("c")) {
            System.out.println("How many dimensions:");
            int dimensions = Integer.parseInt(scanner.nextLine());
            A = new Matrix(dimensions, dimensions);
            coeffs = new double[dimensions];
            System.out.println("Type in your matrix line after line with whitespace as separator:");
            for (int i = 0; i < dimensions; i++) {
                ArrayList<Double> line = (ArrayList<Double>) Arrays.stream(scanner.nextLine().split(" ")).map(Double::parseDouble).collect(Collectors.toList());
                for (int j = 0; j < dimensions; j++) {
                    A.set(i, j, line.get(j));
                }
            }
            System.out.println("Type in free coefficients from 1st to last in separate lines:");
            for (int i = 0; i < dimensions; i++) {
                coeffs[i] = Double.parseDouble(scanner.nextLine());
            }
            System.out.println("Type in accuracy level:");
            double acc = Double.parseDouble(scanner.nextLine());

            SimpleIterator.research(A, coeffs, acc);

        } else {
            System.out.println("Wrong option. Restart the program and try again");
            System.exit(1);
        }

    }
}
