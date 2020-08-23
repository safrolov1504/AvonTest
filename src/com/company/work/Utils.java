package com.company.work;

public class Utils {
    //поворачивает матрицу, что бы проверить столбец тем же методом, как и строчку
    public static String[][] turnArray(String[][] array) {
        String result[][] = new String[array[0].length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                result[j][array.length - i - 1] = array[i][j];
            }
        }
        return result;
    }

    //повторачивает матрицу диаганалью, что бы можно было проверить выигрыш диаганали как строчку
    public static String[][] turnDiagonal(String[][] array){
        String result[][] = new String[2*array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                result[i+j][j] = array[i][j];
            }
        }
        return result;
    }

}
