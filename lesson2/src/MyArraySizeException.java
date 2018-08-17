public class MyArraySizeException extends Exception {
    public enum Matrix {ROW,COLUMN};
    final int wrongSize ;
    final int destinationSize;
    final Matrix where;
    public MyArraySizeException(int wrongSize, int destinationSize, Matrix where) {
             super("Matrix has wrong size");
            this.wrongSize = wrongSize;
            this.destinationSize = destinationSize;
            this.where = where;

    }

    @Override
    public String getMessage() {
        switch (where) {
            case COLUMN:
                return "Matrix has wrong count of columns: "+wrongSize+" instead of "+destinationSize;
            case ROW:
                return "Matrix has wrong count of rows: "+wrongSize+" instead of "+destinationSize;
            default:
                return "Matrix has wrong count of rows or columns: "+wrongSize+" instead of "+destinationSize;
        }
    }
}
