package com.booleanuk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class Question {
    private String question;
    private String correctAnswer;
    private String[] possibleAnswers;

    public Question () {}

    public Question(
//            @JsonProperty("question")
            String question,
//            @JsonProperty("correctAnswer")
            String correctAnswer,
//            @JsonProperty("possibleAnswers")
            String... possibleAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.possibleAnswers = possibleAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String[] getPossibleAnswers() {
        return possibleAnswers;
    }
}