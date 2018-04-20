# Сборка:
./gradlew jar

# Пример использования:
```
        final ListSparseMatrixSupport matrixSupport = new ListSparseMatrixSupport();
        final ListSparseMatrix a = matrixSupport.fromStream(Stream.of(2, 2, 0, 0, 0, 0));
        final ListSparseMatrix b = matrixSupport.fromStream(Stream.of(2, 2, 0, 0, 0, 0));
        final ListSparseMatrix multiply = matrixSupport.multiply(a, b);
```


# Идея решения:
Взять все точки второй матрицы, и переложить их в сортированную по столбцам коллекцию взять все точки первой матрицы,
 пробегая по ним собирать строки и для каждой собранной строки
 бежать по коллекции точек второй матрицы (сортированной по столбцам)
собирая столбцы и перемножая собранные стркоу и столбец.