package com.example.admin.bubblemaths;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class Question {
    private static float answer;
    private static String question;
    private Random numGenerator = new Random();
    Question(){


    }
    public void makeAnswerZero(){
        answer = 0;
    }
    private float getAnswer(){
        return answer;
    }
    public String getAnswerString(){
        return Float.toString(getAnswer());
    }

    void buildQuestion(){
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
                float numberToDivide1 = numGenerator.nextInt(maxNumDivision) + 1;
                float numberToDivide2 = numGenerator.nextInt(maxNumDivision) + 1;
                while (numberToDivide2 > numberToDivide1){
                    numberToDivide2 = numGenerator.nextInt(maxNumDivision) + 1;
                }
                question = Float.toString(numberToDivide1) + " / " + Float.toString(numberToDivide2);
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
        }





    private String getOperators(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.getContextOfApplication());
        Set<String> operators = sharedPreferences.getStringSet("mathOperatorsPref", null);
        assert operators != null;
        return operators.toString();
    }

    String getQuestion(){
        return question;
    }

    String getFakeAnswerString(){
        float fakeAnswerMargin = 50;
        float fakeAnswer;
        float minValFakeAnswer = answer - fakeAnswerMargin;
        float maxValFakeAnswer = answer + fakeAnswerMargin;
        fakeAnswer = numGenerator.nextFloat() * (maxValFakeAnswer - minValFakeAnswer) + minValFakeAnswer;
        if (answer == (int) answer){
            return String.format(Locale.US, "%.0f", fakeAnswer);
        }else {
            return String.format(Locale.US, "%.2f", fakeAnswer);
        }
    }
}
