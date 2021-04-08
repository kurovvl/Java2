package GU.Lesson2;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws MyArraySizeException, MyArrayDataException {

        String[][] arr1 = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"},
        };


        String[][] arr2 = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "SIGN", "11", "12"},
                {"13", "14", "15", "16"},
        };

        String[][] arr3 = {
                {"1", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"},
        };
        String[][] arr4 = {
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"},
        };

        var arrays = new String[][][]{arr1, arr2, arr3, arr4};
        
        for (String[][] array : arrays) {
            try {
                System.out.println(sumArrayElements(array));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

    }

    static int sumArrayElements(String[][] arr) throws MyArraySizeException, MyArrayDataException {

        if (arr.length != 4 || !Arrays.stream(arr).allMatch(e -> e.length == arr.length))
            throw new MyArraySizeException("Неправильный размер массива");
        int resSum = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j].matches("[0-9]+"))
                    resSum += Integer.parseInt(arr[i][j]);
                else throw new MyArrayDataException(i,j);
            }
        }
        return resSum;
    }

}
