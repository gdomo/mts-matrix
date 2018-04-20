import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BasicTests {
    @Test
    public void stressTest() {
        final ListSparseMatrixSupport matrixSupport = new ListSparseMatrixSupport();
        final int size = 10000;
        final ListSparseMatrix a = matrixSupport.fromStream(Stream.concat(Stream.of(size, size), Stream.generate(() -> 0).limit((long) size * size)));
        final ListSparseMatrix b = matrixSupport.fromStream(Stream.concat(Stream.of(size, size), Stream.generate(() -> 0).limit((long) size * size)));
        final List<Integer> actual = matrixSupport.toStream(matrixSupport.multiply(a, b)).filter(i -> i != 0).collect(Collectors.toList());
        assertThat(actual, is(Arrays.asList(size, size)));
    }

    @Test
    public void basicMultiply() {
        final ListSparseMatrixSupport matrixSupport = new ListSparseMatrixSupport();
        final ListSparseMatrix a = matrixSupport.fromStream(Stream.of(3, 2, 2, 1, -3, 0, 4, -1));
        final ListSparseMatrix b = matrixSupport.fromStream(Stream.of(2, 3, 5, -1, 6, -3, 0, 7));
        final List<Integer> actual = matrixSupport.toStream(matrixSupport.multiply(a, b)).filter(i -> i != 0).collect(Collectors.toList());
        assertThat(actual, is(Arrays.asList(3, 3, 7, -2, 19, -15, 3, -18, 23, -4, 17)));
    }

    @Test
    public void endingZeros() {
        final ListSparseMatrixSupport matrixSupport = new ListSparseMatrixSupport();
        final ListSparseMatrix a = matrixSupport.fromStream(Stream.of(3, 2, 1, 2, 3, 4, 0, 6));
        final ListSparseMatrix b = matrixSupport.fromStream(Stream.of(2, 3, 1, 2, 3, 4, 5, 0));
        final List<Integer> actual = matrixSupport.toStream(matrixSupport.multiply(a, b)).collect(Collectors.toList());
        assertThat(actual, is(Arrays.asList(3, 3, 9, 12, 3, 19, 26, 9, 24, 30, 0)));
    }

    @Test
    public void zeros() {
        final ListSparseMatrixSupport matrixSupport = new ListSparseMatrixSupport();
        final ListSparseMatrix a = matrixSupport.fromStream(Stream.of(2, 2, 0, 0, 0, 0));
        final ListSparseMatrix b = matrixSupport.fromStream(Stream.of(2, 2, 0, 0, 0, 0));
        final List<Integer> actual = matrixSupport.toStream(matrixSupport.multiply(a, b)).collect(Collectors.toList());
        assertThat(actual, is(Arrays.asList(2, 2, 0, 0, 0, 0)));
    }
}
