package com.example.lab4right;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController{

    @FXML
    private Label text;


    private final int maxSymbols = 11;
    private boolean start = true;
    private boolean operatorEntered;
    private Counter counter;
    private boolean commaEntered;
    private boolean modEntered;
    private String lastEnteredElemType = "";

    @FXML
    protected void onDigitClick(ActionEvent event) {
        hasBadNotes();
        if(start){
            counter = new Counter();
            operatorEntered = false;
            commaEntered = false;
            modEntered = false;
            text.setText("");
            start = false;
        }
        if (text.getText().length() >= maxSymbols){
            return;
        }
        if (text.getText().equals("0") || lastEnteredElemType.equals("Operator")){
            text.setText("");
        }
        String value = ((Button)event.getSource()).getText();

        String toWrite = text.getText() + value;
        if (toWrite.length() > 11){
            text.setText(toWrite.substring(0, 12));
        }
        else {
            text.setText(toWrite);
        }


        lastEnteredElemType = "Digit";



    }

    @FXML
    protected void onOperatorClick(ActionEvent event){
        if (hasBadNotes() || lastEnteredElemType.equals("Operator") || start || lastEnteredElemType.equals("Comma")){
            return;
        }

        String value = ((Button)event.getSource()).getText();
        if (operatorEntered || modEntered){
            counter.setNum2(text.getText());
            counter.setNum1(counter.count());
            text.setText(value);
        }
        else {
            counter.setNum1(text.getText());
        }
        operatorEntered = true;

        lastEnteredElemType = "Operator";
        counter.setOperator(value);
        text.setText(value);
        commaEntered = false;


        modEntered = false;



    }
    @FXML
    protected void onPercentClick(){
        if (start || modEntered || hasBadNotes() || !operatorEntered || lastEnteredElemType.equals("Comma") ||
                lastEnteredElemType.equals("Operator")){
            return;
        }
        counter.percent(text);
        lastEnteredElemType = "Digit";
        operatorEntered = false;
        if (text.getText().contains(".")){
            checkExtraNulls();
        }
        commaEntered = hasComma();
        result();
    }
    @FXML
    public void result (){
        if (start || hasBadNotes() || !operatorEntered || lastEnteredElemType.equals("Comma") ||
                lastEnteredElemType.equals("Operator")){
            return;
        }
        counter.setNum2(text.getText());
        String res = counter.count();
        if (res.length() > 11){
            if(res.contains("E")){
                String partE = res.substring(res.indexOf("E"));
                int partELength = partE.length();
                String resultString = res.substring(0, maxSymbols - partELength + 1) + partE;
                text.setText(resultString);
            }
            else {
                text.setText(res.substring(0, 12));
            }
            counter.setNum1(res);
        }
        else {
            text.setText(res);
        }

        lastEnteredElemType = "Digit";
        operatorEntered = false;
        counter.setNum1(res);
        counter.setNum2("");
        modEntered = false;
        commaEntered = hasComma();
        if (text.getText().contains(".")){
           checkExtraNulls();
        }

    }
    private void checkExtraNulls(){
        StringBuilder afterComma = new StringBuilder();
        StringBuilder allNulls = new StringBuilder();
        for (int i = text.getText().indexOf(".") + 1; i < text.getText().length(); ++i){
            afterComma.append(text.getText().charAt(i));
            allNulls.append("0");
        }
        if (afterComma.toString().equals(allNulls.toString())){
            text.setText(text.getText().substring(0, text.getText().indexOf(".")));
        }
    }
    private boolean hasComma(){
        return text.getText().contains(".");
    }
    @FXML
    protected void onOperatorUnoClick(ActionEvent event){
        if (start || hasBadNotes()){
            return;
        }
       if (lastEnteredElemType.equals("Digit")){
           String num2 = "";
           String num1 = "";


           if (operatorEntered || modEntered){
               counter.setNum2(text.getText());
               num2 = counter.getNum2();
           }
           else {
               counter.setNum1(text.getText());
               num1 = counter.getNum1();
           }
           String value = ((Button)event.getSource()).getText();
           if (value.equals("1/x") && text.getText().equals("")){
                return;
           }
           counter.setOperatorUno(value);
           String res = counter.count();
           if (modEntered){
               if (Double.parseDouble(res) != (int) Double.parseDouble(res)){
                   return;
               }
           }

           if (res.length() > 11){
               if(res.contains("E")){
                   String partE = res.substring(res.indexOf("E"));
                   int partELength = partE.length();
                   String resultString = res.substring(0, maxSymbols - partELength + 1) + partE;
                   text.setText(resultString);
               }
               else {
                   text.setText(res.substring(0, 12));
               }
           }
           else {
               text.setText(res);
           }
           if (text.getText().contains(".")){
               checkExtraNulls();
           }
           commaEntered = hasComma();

           //modEntered = false;
       }
    }

    @FXML
    protected void onDeleteAllClick(){
        if (start){
            return;
        }
        start = true;
        text.setText("0");
        counter.setNum1("");
        counter.setNum2("");
        operatorEntered = false;
        lastEnteredElemType = "";
        counter.setOperator("");
        counter.setOperatorUno("");
        modEntered = false;
        commaEntered = false;
    }

    public boolean hasBadNotes(){
        if (text.getText().equals("Деление на0!") || text.getText().equals("NaN") || text.getText().equals("Infinity")){
            start = true;
            return true;
        }
        return false;
    }

    @FXML
    protected void onModClick(){
        if (start || modEntered || hasBadNotes()){
            return;
        }
        if (lastEnteredElemType.equals("Digit") && !text.getText().contains(".") && !operatorEntered && !modEntered){
            counter.setOperator("mod");
            counter.setNum1(text.getText());
            lastEnteredElemType = "Operator";
            modEntered = true;
            operatorEntered = true;
            text.setText("mod");
        }
    }

    private String receiveLastType(){
        if(text.getText().length() > 0){
            var lastElem = text.getText().charAt(text.getText().length()-1);
            if (Character.isDigit(lastElem)){
                return "Digit";
            }

            if (lastElem == '.'){
                return "Comma";
            }
        }
        if (operatorEntered){
            return "Operator";
        }
        return "";


    }

    @FXML
    protected void onDeleteLastClick(){
        if (hasBadNotes() || text.getText().contains("E") || start || lastEnteredElemType.equals("Operator") ||
                text.getText().length() == 0){
            return;
        }
        if (text.getText().length() == 1 && !operatorEntered){
            onDeleteAllClick();
            return;
        }
        if (text.getText().substring(text.getText().length() - 1).equals(".")){
            commaEntered = false;
        }
        text.setText(text.getText().substring(0, text.getText().length() - 1));
        lastEnteredElemType = receiveLastType();


    }
    @FXML
    protected void changePlusMinus(){

        if (hasBadNotes() || lastEnteredElemType.equals("Operator") || text.getText().length() >= maxSymbols &&
                text.getText().charAt(0) != '-' || start || text.getText().equals("0") ||
                lastEnteredElemType.equals("Comma")){
            return;
        }

        if (text.getText().charAt(0) == '-'){
            text.setText(text.getText().substring(1));
        }
        else{
            text.setText("-" + text.getText());
        }

    }

    @FXML
    protected void onCommaClick(ActionEvent event){
        if (start || modEntered || hasBadNotes() || text.getText().contains(".") ||
                text.getText().length() >= maxSymbols || !lastEnteredElemType.equals("Digit") && !start){
            return;
        }

        String value = ((Button)event.getSource()).getText();
        text.setText(text.getText() + value);

        lastEnteredElemType = "Comma";
        commaEntered = true;

    }
}