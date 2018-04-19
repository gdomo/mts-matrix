import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        final BidirectionalSparseMatrixSupport matrixSupport = new BidirectionalSparseMatrixSupport();
        BidirectionalSparseMatrix a = matrixSupport.fromStream(Stream.of(3, 2, 2, 1, -3, 0, 4, -1));
        BidirectionalSparseMatrix b = matrixSupport.fromStream(Stream.of(2, 3, 5, -1, 6, -3, 0, 7));
        matrixSupport.toStream(matrixSupport.multiply(a, b)).forEach(System.out::println);

        a = matrixSupport.fromStream(Stream.concat(Stream.of(100000, 100000), Stream.generate(() -> 0).limit(100000L*100000)));
        b = matrixSupport.fromStream(Stream.concat(Stream.of(100000, 100000), Stream.generate(() -> 0).limit(100000L*100000)));
        matrixSupport.multiply(a, b);
    }
}
