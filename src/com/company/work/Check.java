package com.company.work;

import com.company.data.Numbers;

public class Check {


    //проверка, что поле заполнено
    //true - поле заполнено
    public static boolean checkFullField(String[][] field){
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if(field[i][j] == null){
                    return false;
                }
            }
        }
        return true;
    }

    //проверяет выиграш по строчке или столбцам
    //true - выиграл игрок
    public static boolean checkWinner(String [][] fieldTmp, String newMove){

        int count = 0;
        for (int i = fieldTmp.length-1; i >=0 ; i--) {
            for (int j = 0; j < fieldTmp[i].length; j++) {
                if(fieldTmp[i][j] != null  && fieldTmp[i][j].equals(newMove)){
                    count++;
                } else {
                    count = 0;
                }
                if(count > Numbers.WIN){
                    return true;
                }
            }
            count = 0;
        }
        return false;
    }
}
