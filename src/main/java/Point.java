public class Point {
    private final int column;
    private final int row;
    private final int value;

    public Point(int column, int row, int value) {
//        if (column < 0 || row < 0) {
//            throw new IllegalArgumentException();
//        }
        this.column = column;
        this.row = row;
        this.value = value;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (column != point.column) return false;
        if (row != point.row) return false;
        return value == point.value;

    }

    @Override
    public int hashCode() {
        int result = column;
        result = 31 * result + row;
        result = 31 * result + value;
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "column=" + column +
                ", row=" + row +
                ", value=" + value +
                '}';
    }
}
