# Сборка:
./gradlew jar

# Пример использования:
```
        final ListSparseMatrixSupport matrixSupport = new ListSparseMatrixSupport();
        final ListSparseMatrix a = matrixSupport.fromStream(Stream.of(2, 2, 0, 0, 0, 0));
        final ListSparseMatrix b = matrixSupport.fromStream(Stream.of(2, 2, 0, 0, 0, 0));
        final ListSparseMatrix multiply = matrixSupport.multiply(a, b);
```
