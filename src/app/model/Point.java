package app.model;

public class Point {
    public final int row;
    public final int column;

    public Point(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Point(String index) {
        String[] split = index.split(",");
        this.row = Integer.parseInt(split[0]);
        this.column = Integer.parseInt(split[1]);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}