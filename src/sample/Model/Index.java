package sample.Model;

public class Index {
    public final int row;
    public final int column;

    public Index(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Index(String index) {
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