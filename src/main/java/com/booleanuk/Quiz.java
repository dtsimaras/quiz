package com.booleanuk;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Paths;
import java.util.*;

public class Quiz {
    private List<Question> questions = new ArrayList<>();
    private static Quiz instance;

    // TODO: Decide singleton or static? Depends on how I expand
    public static Quiz getInstance() {
        if (instance == null) {
            instance = new Quiz();
        }
        return instance;
    }

    // TODO: add more checks. Check if possibleAnswers = 3 or 4
    //  check if possibleAnswers include correctAnswer, check on read/write
    //  check if any question field isEmpty - avoid Exception & Errors
    //  We can't have Y and N as possible answers, just to be safe
    public void run() {
        UserInterface.showMessage("""
                Welcome to the best Quiz!
                Would you like to give it a try?(Y/N)""");

        boolean shouldStartQuiz = isUserProceeding();
        if (shouldStartQuiz)
            startQuiz();
        else
            UserInterface.showMessage("Exiting Quiz. See you next time!");
    }

    private void startQuiz() {
        loadQuestions();

        int questionsAsked = 0;
        int correctAnswers = 0;

        for (Question question : questions) {
            UserInterface.showMessage("Please answer the questions by typing A, B, C or D");
            // Show Question
            UserInterface.showMessage(question.getQuestion());
            questionsAsked++;

            // Map a letter to each answer
            char key = 'A';
            Map<Character, String> possibleAnswersMap = new HashMap<>();
            for (String possibleAnswer : question.getPossibleAnswers()) {
                possibleAnswersMap.put(key++, possibleAnswer);
            }
            // Show possible answers
            possibleAnswersMap.forEach((k, v) -> UserInterface.showMessage(k + ": " + v));

            // Check answer and respond with appropriate message
            char selection = UserInterface.getUserInput();
            if (possibleAnswersMap.get(selection).equals(question.getCorrectAnswer())) {
                UserInterface.showMessage("Your answer was correct!\n");
                correctAnswers++;
            } else {
                UserInterface.showMessage("The correct answer was " + question.getCorrectAnswer() + "\n");
            }

            // Ask user if he wants to continue to the next question
            boolean hasNext = questionsAsked < questions.size();
            if (hasNext) {
                UserInterface.showMessage("Would you like to continue to the next question? (Y/N)");
                boolean shouldContinue = isUserProceeding();
                if (!shouldContinue) break;
            }
        }
        UserInterface.showMessage("You answered " + correctAnswers + " out of " + questionsAsked + " correctly! Thank you for playing!");
    }

    private static boolean isUserProceeding() {
        char userInput = UserInterface.getUserInput();
        if (userInput == 'Y') return true;
        else if (userInput == 'N') return false;
        else {
            UserInterface.showMessage("Please enter Y for Yes or N for No");
            return isUserProceeding();
        }
    }

    private void loadQuestions() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.questions = Arrays.asList(mapper.readValue(Paths.get("src/main/java/com/booleanuk/questions.json").toFile(), Question[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
