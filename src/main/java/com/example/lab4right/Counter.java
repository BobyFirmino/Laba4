package com.example.lab4right;

import javafx.scene.control.Label;

public class Counter {

    private String num1;
    private String num2;
    private String operator;
    private String operatorUno;

    public String getOperatorUno() {
        return operatorUno;
    }

    public void setOperatorUno(String operatorUno) {
        this.operatorUno = operatorUno;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum2() {
        return num2;
    }
    private String result(String res){
        return res;
    }

    public Counter() {
        num1 = "0";
        num2 = "0";
        operator = "";
        operatorUno = "";

    }
    public String count(){
        if (!operatorUno.equals("")){

            return countUno();
        }
        return Binary();

    }
    private String countMod(){
        operator = "";
        if (num2.equals("0")){
            return "Деление на0!";
        }
        return result(Long.toString(Math.floorMod((long)Double.parseDouble(num1), (long)Double.parseDouble(num2))));
    }
    private String Binary(){
        switch (operator){
            case "+" : return addition();
            case "-" : return subtraction();
            case "*" : return multiplication();
            case "/" : return division();
            case "mod": return countMod();
            default: return power();
        }

    }
    private String countUno(){
        if(operatorUno.equals("sqrt")){
            return sqrt();
        }
        else {
           return inverse();
        }

    }
    private String inverse(){
        System.out.println("1 " +num1);
        System.out.println("2 " +num2);
        System.out.println();
        if ((num1) == "0" || num2 == "" || num2 == "0")
            return "Деление на 0";
        if (operator.equals("")){
            num1 = Double.toString(1/Double.parseDouble(num1));
            operatorUno = "";
            return num1;
        }
        else {
            num2 = Double.toString(1/Double.parseDouble(num2));
            operatorUno = "";

            return num2;
        }

    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    private String addition(){
        operator = "";

        return result(Double.toString(Double.parseDouble(num1) + Double.parseDouble(num2)));



    }
    private String subtraction(){
        operator = "";

        return result(Double.toString(Double.parseDouble(num1) - Double.parseDouble(num2)));


    }
    private String multiplication(){
        operator = "";

        return result(Double.toString(Double.parseDouble(num1) * Double.parseDouble(num2)));


    }
    private String division(){
        operator = "";

        if (Double.parseDouble(num2) == 0){
            num1 = "";
            num2 = "";

            operator = "";
            operatorUno = "";
            return "Деление на0!";
        }
        return result(Double.toString(Double.parseDouble(num1)/ Double.parseDouble(num2)));


    }

    public void percent(Label text){
        String mainOperator = operator;
        operator = "*";
        num2 = text.getText();//получаем второе число

        double percentDouble = Double.parseDouble(num2) / 100;
        num2 = Double.toString(percentDouble);


        num2 = count();
        operator = mainOperator;
        num2 =count();
        if (num2.length() > 11){
            text.setText(num2.substring(0, 12));
        }
        else {
            text.setText(num2);
        }

    }
    private String sqrt(){
        if (operator.equals("")){
            num1 = Double.toString(Math.sqrt(Double.parseDouble(num1)));
            operatorUno = "";

            return result(num1);
        }
        else {
            num2 = Double.toString(Math.sqrt(Double.parseDouble(num2)));
            operatorUno = "";

            return result(num2);
        }

    }
    private String power(){
        operator = "";

        return result(Double.toString(Math.pow(Double.parseDouble(num1), Double.parseDouble(num2))));

    }

}
