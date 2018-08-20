import java.util.Arrays;

/**
 * Java 2 course by GeekBrains. Homework 2.
 * @author A.Usmanov
 * @version dated 2018-08-17
 * 1. Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4, при подаче массива другого размера необходимо бросить исключение MyArraySizeException.
 * 2. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и просуммировать. Если в каком-то элементе массива преобразование не удалось (например, в ячейке лежит символ или текст вместо числа), должно быть брошено исключение MyArrayDataException, с детализацией в какой именно ячейке лежат неверные данные.
 * 3. В методе main() вызвать полученный метод, обработать возможные исключения MySizeArrayException и MyArrayDataException, и вывести результат расчета.
 * Или творите на своё усмотрение! Интересная работа будет в обзоре!
 */
public class Homework2 {
    private static final int MATRIX_SIZE = 4;

    public static void main(String[] args)  {
        System.out.println("Test wrong count of rows");
        test(new String[][] {{"0","1","2"},{"0","1","2"},{"0","1","2"}});
        System.out.println("Test wrong count of columns");
        test(new String[][] {{"0","1","2"},{"0","1","2"},{"0","1","2"},{"0","1","2"}});
        System.out.println("Test wrong data");
        test(new String[][] {{"0", "1", "2", "A"}, {"0", "1", "2", "B"}, {"0", "1", "2","2"}, {"0", "1", "2", "C"}});
        System.out.println("Test correct data");
        test(new String[][] {{"0", "1", "2", "3"}, {"0", "1", "2", "4"}, {"0", "1", "2","3"}, {"0", "1", "2", "4"}});
    }
    static void test(String[][] array) {
        int result = 0;
        try {
            result  = calculateMatrixSum(array);
        } catch (MyArrayDataException e) {
            System.out.println(e.getMessage());
        } catch (MyArraySizeException e) {
            System.out.println(e.getMessage());
        }
        finally {
            printArray(array,result);
        }
    }
    static int calculateMatrixSum(String[][] matrix) throws MyArraySizeException,MyArrayDataException {
        int result = 0;
        if (matrix.length!=MATRIX_SIZE) throw new MyArraySizeException(matrix.length,MATRIX_SIZE,MyArraySizeException.Matrix.ROW);
        for (int rowIndex = 0;rowIndex<matrix.length;rowIndex++) {
            String[] row = matrix[rowIndex];
            if (row.length!=MATRIX_SIZE) throw new MyArraySizeException(row.length,MATRIX_SIZE,MyArraySizeException.Matrix.COLUMN);
            for (int colIndex = 0;colIndex<matrix.length;colIndex++) {
                try {
                    result+=Integer.parseInt(row[colIndex]);
                }
                catch (NumberFormatException e) {
                    throw new MyArrayDataException(rowIndex,colIndex);
                }
                catch (NullPointerException e) {
                    throw new MyArrayDataException(rowIndex,colIndex);
                }
            }
        }
        return result;
    }

    private static void printArray(String[][] array, int total) {
        int maxColumns = 0;
        for (String[] row:array) {
            System.out.println(Arrays.toString(row));
            if (row.length>maxColumns) maxColumns++;
        }
        for (int i = 0;i<maxColumns*3;i++) System.out.print("-");
        System.out.println();
        System.out.println("Total: "+total);
        for (int i = 0;i<maxColumns*3;i++) System.out.print("-");
        System.out.println();
    }

}
