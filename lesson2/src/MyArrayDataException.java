public class MyArrayDataException extends Exception {
    public MyArrayDataException(int row, int column) {
        super("Invalid data in cell ["+row+","+column+"]");
    }
}
