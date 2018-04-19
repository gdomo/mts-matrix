import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class BidirectionalSparseMatrix {
    private final List<Point> pointsByRows;
    private final int rows;
    private final int columns;


    /**
     * @param stream Первый элемент - количество строк, второй количество столбцов.
     *               Далее - элементы матрицы слева-направо и сверху-вниз. Null не допускается.
     *               Stream не должен быть parallel
     */
    public BidirectionalSparseMatrix(Stream<Integer> stream) {
        final AtomicLong streamCounter = new AtomicLong(0);
        final int[] rowsColumns = new int[2];
        final List<Point> pointsByRows = new ArrayList<>();
        stream
                .forEach(value -> {
                    long count = streamCounter.getAndIncrement();
                    if (count == 0) {
                        rowsColumns[0] = value;
                    } else if (count == 1) {
                        rowsColumns[1] = value;
                    } else {
                        if (value != 0) {
                            count = count - 2; //теперь count - это номер элемента с матрице, а не стриме
                            final Point point = new Point((int) (count % rowsColumns[1]), (int) (count / rowsColumns[1]), value);
                            pointsByRows.add(point);
                        }
                    }
                });
        if (streamCounter.get() < 2) {
            throw new IllegalArgumentException();
        }
        this.rows = rowsColumns[0];
        this.columns = rowsColumns[1];
        this.pointsByRows = Collections.unmodifiableList(pointsByRows);
    }

    public BidirectionalSparseMatrix(List<Point> pointsByRows, int rows, int columns) {
        this.pointsByRows = Collections.unmodifiableList(new ArrayList<>(pointsByRows));
        this.rows = rows;
        this.columns = columns;
    }

    public Stream<Integer> toStream() {
        final AtomicReference<Point> previous = new AtomicReference<>(new Point(columns - 1, -1, 0));
        return Stream.of(
                Stream.of(rows, columns),
                Stream.concat(pointsByRows.stream(), Stream.of(new Point(0, rows, 0))) //добавление "закрывающей точки"
                        .flatMap(point -> {
                            final Stream<Integer> stream = Stream.concat(
                                    zeroStreamBetween(previous, point),
                                    point.getRow() < rows ? Stream.of(point.getValue()) : zeroStreamBetween(previous, point));
                            previous.set(point);
                            return stream;
                        }))
                .flatMap(stream -> stream);
    }

    private Stream<Integer> zeroStreamBetween(AtomicReference<Point> previous, Point point) {
        final Point from = previous.get();
        return Stream.generate(() -> 0)
                .limit((point.getRow() - from.getRow()) * columns + point.getColumn() - from.getColumn() - 1);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Point> getPointsByRows() {
        return pointsByRows;
    }
}
