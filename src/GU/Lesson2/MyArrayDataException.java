package GU.Lesson2;

public class MyArrayDataException extends Exception {
    public MyArrayDataException(int row, int col) {
        super(String.format("Неверные данные в строке %d, столбец %d", row, col));
    }
}
