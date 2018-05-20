package com.example.admin.bubblemaths;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Question {
    private static int answer;
    private static String question;
    Random numGenerator = new Random();
    Question(){


    }
    public void makeAnswerZero(){
        answer = 0;
    }
    private int getAnswer(){
        return answer;
    }
    public String getAnswerString(){
        return Integer.toString(getAnswer());
    }

    String buildQuestion(){
        int maxNumAddition = 25;
        int maxNumSubtraction = 25;
        int maxNumDivision = 25;
        int maxNumMultiplication = 25;
        String operatorsString = getOperators();
        operatorsString =  operatorsString.replace("[", "").replace("]","").replace(" ","");
        String[] operatorsList = operatorsString.split(",");
//        Log.i("Test", Integer.toString(numberOfOperators));
        int randomOperatorPicker = numGenerator.nextInt(operatorsList.length);
        String chosenOperator = operatorsList[randomOperatorPicker];
        switch (chosenOperator){
            case("Addition"):
                int numberToAdd1 = numGenerator.nextInt(maxNumAddition) + 1;
                int numberToAdd2 = numGenerator.nextInt(maxNumAddition) + 1;
                question = Integer.toString(numberToAdd1) + " + " + Integer.toString(numberToAdd2);
                answer = numberToAdd1 + numberToAdd2;
                break;
            case("Division"):
                int numberToDivide1 = numGenerator.nextInt(maxNumDivision) + 1;
                int numberToDivide2 = numGenerator.nextInt(maxNumDivision) + 1;
                while (numberToDivide2 > numberToDivide1){
                    numberToDivide2 = numGenerator.nextInt(maxNumDivision) + 1;
                }
                question = Integer.toString(numberToDivide1) + " / " + Integer.toString(numberToDivide2);
                answer = numberToDivide1 / numberToDivide2;
                break;
            case("Subtraction"):
                int numberToSubtract1 = numGenerator.nextInt(maxNumSubtraction) + 1;
                int numberToSubtract2 = numGenerator.nextInt(maxNumSubtraction) + 1;
                question = Integer.toString(numberToSubtract1) + " - " + Integer.toString(numberToSubtract2);
                answer = numberToSubtract1 - numberToSubtract2;
                break;
            case("Multiplication"):
                int numberToMultiply1 = numGenerator.nextInt(maxNumMultiplication) + 1;
                int numberToMultiply2 = numGenerator.nextInt(maxNumMultiplication) + 1;
                question = Integer.toString(numberToMultiply1) + " * " + Integer.toString(numberToMultiply2);
                answer = numberToMultiply1 *  numberToMultiply2;
                break;
        }
        return question;
        }





    String getOperators(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.getContextOfApplication());
        Set<String> operators = sharedPreferences.getStringSet("mathOperatorsPref", null);
        return operators.toString();
    }

    String getQuestion(){
        return question;
    }

    String getFakeAnswerString(){
        int fakeAnswerMargin = 50;
        int fakeAnswer;
        int minValFakeAnswer = answer - fakeAnswerMargin;
        int maxValFakeAnswer = answer + fakeAnswerMargin;
        fakeAnswer = numGenerator.nextInt((maxValFakeAnswer - minValFakeAnswer) + 1) + minValFakeAnswer;
        return Integer.toString(fakeAnswer);
    }
}
