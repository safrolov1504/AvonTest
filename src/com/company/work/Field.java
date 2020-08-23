package com.company.work;

import com.company.data.Numbers;
import com.company.data.Status;

import java.util.Scanner;

public class Field {
    private Scanner input = new Scanner(System.in);
    private String[][] field;
    private String[][] fieldTmp;
    private int step = 1;
    private String out;
    private String newMove;
    private Integer num;
    private String numString;

    public Field() {
        field = new String[Numbers.ROW][Numbers.COLUMN];
        fieldTmp = new String[Numbers.ROW][Numbers.COLUMN];
    }

    public void play(){
        System.out.println("Game started.");
        Status status = Status.OK;
        while (true){
            //проверяет чей ход
            choosePlayer();

            //печатаем поле
            if (status == Status.OK){
                printField();
            }

            //вводим данные и проверяем введенные данных
            System.out.println("if you want to stop, please write 0");
            System.out.print(out + " - choose column (1-7): ");

            status = insertData();
            if(status == Status.STOP){
                System.out.println("\nYour game is finished!");
                break;
            } else if(status == Status.ERROR){
                System.out.println("\nChose the wrong column. Please write again");
                continue;
            }

            //вносим шаг в таблицу
            status = move(num);
            if(status == Status.ERROR){
                System.out.println("\nThe column "+ num + " is full. Please choose the other one.");
                continue;
            }

            //проверяем поле
            status = check();
            if(status == Status.FULL){
                printField();
                System.out.println("\nThe field is full. Your game is finished!");
                break;
            } else if(status == Status.WIN){
                printField();
                System.out.println("\n"+ out + " is winner! Your game is finished!");
                break;
            }

        }
    }



    //отвечает за введение данных и проверки правильности ввода
    private Status insertData(){
        //проверяет правильность введенных данных
        numString = input.nextLine();
        try {
            num = Integer.parseInt(numString);
            if(num<0 || num > Numbers.COLUMN){
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            return Status.ERROR;
        }

        //проверка желания выйти из игры
        if(num == 0){
            return Status.STOP;
        }

        return Status.OK;
    }

    //делает шаг
    private Status move(int num) {
        int lastEmptyRow = sendLastFilledColumn(num-1);

        if(lastEmptyRow >= 0){
            field[lastEmptyRow][num-1] = newMove;
            step++;
        } else {
            return Status.ERROR;
        }
        return Status.OK;
    }

    //проверяет заполненость поля и победителя
    private Status check() {
        if(Check.checkFullField(field)){
            return Status.FULL;
        }
        if(checkWinner(Status.ROW)){
            return Status.WIN;
        }
        if(checkWinner(Status.COLUMN)){
            return Status.WIN;
        }
        if(checkWinner(Status.DIAG_1)){
            return Status.WIN;
        }
        if(checkWinner(Status.DIAG_2)){
            return Status.WIN;
        }
        return Status.OK;
    }


    //печатет поле
    private void printField(){
//        System.out.print("|");
        for (int i = 0; i < field.length; i++) {
            System.out.print("|");
            for (int j = 0; j < field[i].length; j++) {
                if(field[i][j] == null){
                    System.out.print(" |");
                } else {
                    System.out.print(field[i][j] + "|");
                }
            }
            System.out.println();
        }
    }



    //проверяет чей сейчас ход и записывает требуемые строки
    private void choosePlayer(){
        if(step % 2 != 0){
            out = "Player 1 [RED]";
            newMove = "R";
        } else {
            out = "Player 2 [GREEN]";
            newMove = "G";
        }
    }

    //возращает последную заполненую строчку в этой колонке
    private int sendLastFilledColumn(int num){
        for (int i = field.length-1; i >= 0; i--) {
            if(field[i][num] == null){
                return i;
            }
        }
        return -1;
    }

    //отправляет на проверку поле
    private boolean checkWinner(Status status){
        if(status == Status.ROW){
            fieldTmp = field;
        } else if(status == Status.COLUMN){
            fieldTmp = Utils.turnArray(field);
        } else if(status == Status.DIAG_1){
            fieldTmp = Utils.turnDiagonal(field);
        } else if(status == Status.DIAG_2){
            fieldTmp = Utils.turnArray(field);
            fieldTmp = Utils.turnDiagonal(fieldTmp);
        }
        return Check.checkWinner(fieldTmp, newMove);
    }
}
