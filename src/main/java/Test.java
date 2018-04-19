import java.util.stream.Stream;

/**
 * Created by Hunt on 19.04.2018.
 */
public class Test {
    public static void main(String[] args) {
        final BidirectionalSparseMatrixSupport matrixSupport = new BidirectionalSparseMatrixSupport();
        final BidirectionalSparseMatrix a = matrixSupport.fromStream(Stream.of(3, 2, 2, 1, -3, 0, 4, - 1));
        final BidirectionalSparseMatrix b = matrixSupport.fromStream(Stream.of(2, 3, 5, -1, 6, -3, 0, 7));
        matrixSupport.toStream(matrixSupport.multiply(a, b)).forEach(System.out::println);
    }
}
