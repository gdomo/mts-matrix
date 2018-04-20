import java.util.*;
import java.util.stream.Stream;

//идея решения:
//взять все точки второй матрицы, и переложить их в сортированную по столбцам коллекцию
//взять все точки первой матрицы, пробегая по ним собирать строки и для каждой собранной строки
//бежать по коллекции точек второй матрицы (сортированной по столбцам)
//собирая столбцы и перемножая собранные стркоу и столбец
public class ListSparseMatrixSupport implements SparseMatrixSupport<ListSparseMatrix> {
    @Override
    public Stream<Integer> toStream(ListSparseMatrix matrix) {
        return matrix.toStream();
    }

    @Override
    public ListSparseMatrix fromStream(Stream<Integer> stream) {
        return new ListSparseMatrix(stream);
    }

    @Override
    public ListSparseMatrix multiply(ListSparseMatrix first, ListSparseMatrix second) {
        if (first.getColumns() != second.getRows()) {
            throw new IllegalArgumentException();
        }
        final List<Point> firstByRows = new ArrayList<>(first.getPointsByRows());
        final List<Point> secondByRows = second.getPointsByRows();
        if (firstByRows.size() == 0 || secondByRows.size() == 0) {
            return new ListSparseMatrix(Collections.emptyList(), first.getRows(), second.getColumns());
        }
        final SortedSet<Point> secondByColumns = new TreeSet<>((p1, p2) -> {
            if (p1.getColumn() == p2.getColumn()) {
                return Integer.compare(p1.getRow(), p2.getRow());
            } else {
                return Integer.compare(p1.getColumn(), p2.getColumn());
            }
        });
        secondByColumns.addAll(secondByRows);
        firstByRows.add(new Point(0, first.getRows(), 0)); //"закрывающий" Point для лакончиности циклов
        secondByColumns.add(new Point(second.getColumns(), 0, 0)); //"закрывающий" Point для лакончиности циклов

        final ArrayList<Point> productByRows = new ArrayList<>();
        int currentRow = 0;
        final ArrayList<Point> rowPoints = new ArrayList<>();
        for (Point rowPoint : firstByRows) {
            //итератор "по строкам" первой таблицы
            if (rowPoint.getRow() == currentRow) {
                rowPoints.add(rowPoint);
            } else { //получен первый элемент следующей строки, текущая готова
                final ArrayList<Point> columnPoints = new ArrayList<>();
                int currentColumn = 0;
                //сбор столбца
                for (Point columnPoint : secondByColumns) {
                    if (columnPoint.getColumn() == currentColumn) {
                        columnPoints.add(columnPoint);
                    } else {//получен первый элемент следующего столбца - текущие строка и столбец готовы
                        final int product = multiply(rowPoints, columnPoints);
                        if (product != 0) {
                            productByRows.add(new Point(currentColumn, currentRow, product));
                        }
                        columnPoints.clear();
                        currentColumn = columnPoint.getColumn();
                        columnPoints.add(columnPoint);
                    }
                }
                rowPoints.clear();
                currentRow = rowPoint.getRow();
                rowPoints.add(rowPoint);
            }
        }

        return new ListSparseMatrix(productByRows, first.getRows(), second.getColumns());
    }

    private int multiply(Collection<Point> row, Collection<Point> column) {
        //потенциально, наверное можно оптимизировать и обойтись без копирования умножаемых стркои-столбца в коллекции
        //но так проще и понятнее
        //также модно было бы распарралелить вычисление строк результирующей матрицы
        if (row.size() == 0 || column.size() == 0) {
            return 0;
        }
        int res = 0;
        final Iterator<Point> rowIterator = row.iterator();
        final Iterator<Point> columnIterator = column.iterator();
        Point a = rowIterator.next();
        Point b = columnIterator.next();
        while (true) {
            if (a.getColumn() < b.getRow()) {
                if (rowIterator.hasNext()) {
                    a = rowIterator.next();
                } else {
                    break;
                }
            } else if (a.getColumn() > b.getRow()) {
                if (columnIterator.hasNext()) {
                    b = columnIterator.next();
                } else {
                    break;
                }
            } else {
                res += a.getValue() * b.getValue();
                if (rowIterator.hasNext() && columnIterator.hasNext()) {
                    a = rowIterator.next();
                    b = columnIterator.next();
                } else {
                    break;
                }
            }
        }
        return res;
    }
}
