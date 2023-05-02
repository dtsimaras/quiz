package com.booleanuk;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Paths;
import java.util.*;

public class Quiz {
    private List<Question> questions = new ArrayList<>();
    private static Quiz instance;

    public static Quiz getInstance() {
        if (instance == null) {
            instance = new Quiz();
        }
        return instance;
    }

    public void run() {
        UserInterface.showMessage("""
                Welcome to the best Quiz!
                Would you like to give it a try?(Y/N)""");
        while (true) {
            try {
                char userInput = UserInterface.getUserInput();
                if (userInput == 'Y') {
                    startQuiz();
                    break;
                } else if (userInput == 'N') {
                    UserInterface.showMessage("Exiting Quiz. See you next time!");
                    break;
                } else {
                    UserInterface.showMessage("Please enter Y for Yes or N for No");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startQuiz() {
        loadQuestions();

        int questionsAsked = 0;
        int correctAnswers = 0;

        for (Question question : questions) {
            // Print Question
            UserInterface.showMessage(question.getQuestion());
            questionsAsked++;

            // Map a letter to each answer and show them to the user
            char key = 'A';
            Map<Character, String> possibleAnswersMap = new HashMap<>();
            for (String possibleAnswer : question.getPossibleAnswers()) {
                possibleAnswersMap.put(key++, possibleAnswer);
            }
            possibleAnswersMap.forEach((k, v) -> UserInterface.showMessage(k + ": " + v));

            // Check if the answer is correct and respond with appropriate message
            try {
                char userInput = UserInterface.getUserInput();
                if (possibleAnswersMap.get(userInput).equals(question.getCorrectAnswer())) {
                    UserInterface.showMessage("Your answer was correct!\n");
                    correctAnswers++;
                } else {
                    UserInterface.showMessage("The correct answer was " + question.getCorrectAnswer() + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Ask user if he wants to continue to the next question
            boolean hasNext = questions.size() - 1 >= questionsAsked;
            while (hasNext) {
                UserInterface.showMessage("Would you like to continue to the next question? (Y/N)");
                char userInput = UserInterface.getUserInput();
                if (userInput == 'N') hasNext = false;
                if (userInput == 'Y') {
                    hasNext = true;
                    break;
                }
            }
            if (!hasNext) break;
        }
        UserInterface.showMessage("You answered " + correctAnswers + "out of" + questionsAsked + " correctly! Thank you for playing!\n");
    }

    public void loadQuestions() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.questions = Arrays.asList(mapper.readValue(Paths.get("src/main/java/com/booleanuk/questions.json").toFile(), Question[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
