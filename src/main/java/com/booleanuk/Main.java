package com.booleanuk;

public class Main {
    public static void main(String[] args) {


        Quiz quiz = Quiz.getInstance();
        quiz.createSampleQuestions();
        quiz.run();
    }
}