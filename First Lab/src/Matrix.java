import java.util.ArrayList;

public class Matrix implements Cloneable {


    private final int rows;
    private final int columns;
    private final ArrayList<Double> numbers;

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        numbers = new ArrayList<>(rows * columns);
        for (int i = 0; i < rows * columns; i++) {
            numbers.add((double) 0);
        }
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return columns;
    }

    public ArrayList<Double> getRow(int num) {
        ArrayList<Double> res = new ArrayList<>(columns);
        for (int i = 0; i < columns; i++) {
            res.add((double) 0);
        }
        for (int i = 0; i < columns; i++) {
            res.set(i, numbers.get(columns * num + i));
        }
        return res;
    }

    public void setRow(ArrayList<Double> list, int num) {
        for (int i = 0; i < columns; i++) {
            numbers.set(num * columns + i, list.get(i));
        }
    }

    public ArrayList<Double> getColumn(int num) {
        ArrayList<Double> res = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            res.add((double) 0);
        }
        for (int i = 0; i < rows; i++) {
            res.set(i, numbers.get(columns * i + num));
        }
        return res;
    }

    public Double get(int row, int column) {
        return getRow(row).get(column);
    }

    public void set(int row, int column, Double value) {
        numbers.set(columns * row + column, value);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
