package Functions;

public class Functions {

    public static double functionOne(double x) {
        return -2 * Math.pow(x, 3) - 4 * Math.pow(x, 2) + 8 * x - 4;
    }

    public static double functionTwo(double x) {
        return Math.sin(x) * x;
    }

    public static double functionThree(double x) {
        return Math.pow(Math.asin(Math.abs(Math.pow(x, 6) / 8)), Math.sqrt(Math.abs(0.4 * x)));
    }

}
