package com.booleanuk;

import java.util.HashMap;
import java.util.Map;

public class Question {
    private String question;
    private String correctAnswer;
    private Map<Character, String>  possibleAnswers;
    public Question (String question, String correctAnswer, String... possibleAnswers ) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.possibleAnswers = new HashMap<>();
        char key = 'A';
        for (String answer: possibleAnswers) {
            this.possibleAnswers.put(key++, answer);
        }
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Map<Character, String> getPossibleAnswers() {
        return possibleAnswers;
    }
}