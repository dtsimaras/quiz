package com.booleanuk;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Paths;
import java.util.*;

public class Quiz {
    private List<Question> questions = new ArrayList<>();
    private static Quiz instance;
    private final Scanner scanner = new Scanner(System.in);

    public static Quiz getInstance() {
        if (instance == null) {
            instance = new Quiz();
        }
        return instance;
    }

    public void addQuestions(Question... questions) {
        this.questions.addAll(Arrays.asList(questions));
    }

    public void run() {
        String welcomeText = """
                Welcome to the best Quiz!
                Would you like to give it a try?(y/n)""";
        System.out.println(welcomeText);

        while (true) {
            try {
                char start = scanner.nextLine().trim().toLowerCase().charAt(0);
                if (start == 'y') {
                    loadQuestions();
                    startQuiz();
                    break;
                } else if (start == 'n') {
                    System.out.println("\nExiting quiz");
                    break;
                } else {
                    System.out.println("Please enter y for yes or n for no");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startQuiz() {
        String quizExplanation = "Please answer the following questions:";
        System.out.println("\n" + quizExplanation);

        int questionsAsked = 0;
        int correctAnswers = 0;

        for (Question question : questions) {
            // Print Question and Possible Answers
            System.out.println(question.getQuestion());

            // Map letter to answer and print
            char key = 'A';
            Map<Character, String> possibleAnswersMap = new HashMap<>();
            for (String possibleAnswer : question.getPossibleAnswers()) {
                possibleAnswersMap.put(key++, possibleAnswer);
            }
            possibleAnswersMap.forEach((k, v) -> System.out.println(k + ": " + v));

            // Get user input
            char selection = '0';
            try {
                selection = scanner.nextLine().trim().toUpperCase().charAt(0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Check if the answer is correct and respond with appropriate message
            if (possibleAnswersMap.get(selection).equals(question.getCorrectAnswer())) {
                System.out.println("Your answer was correct!\n");
                correctAnswers++;
            } else {
                System.out.println("The correct answer was " + question.getCorrectAnswer() + "\n");
            }
            questionsAsked++;

            // Ask user if he wants to continue to the next question
            boolean hasNext = questions.size() -1 >= questionsAsked;
            while (hasNext) {
                System.out.println("Would you like to continue to the next question? (y/n)");
                selection = scanner.nextLine().trim().toLowerCase().charAt(0);
                if (selection == 'n')
                    hasNext = false;
                if (selection == 'y') {
                    hasNext = true;
                    break;
                }
            }
            if (!hasNext) break;
        }
        System.out.printf("You answered %d out of %d correctly! Thank you for playing!\n",
                correctAnswers, questionsAsked);
    }

    @Deprecated // This method is replaced with loadQuestions and questions.json
    public void createSampleQuestions() {

        Question q1 = new Question("Where is Greece?",
                "Europe",
                "Asia", "Europe", "America", "Africa");

        Question q2 = new Question("In Greek Mythology, who is the Queen of the Underworld and wife of Hades?",
                "Persephone",
                "Artemis", "Athena", "Persephone", "Hera");

        Question q3 = new Question("Which house was Harry Potter almost sorted into?",
                "Slytherin",
                "Gryffindor", "Hufflepuff", "Ravenclaw", "Slytherin");

        Question q4 = new Question("Which country gifted the Statue of Liberty to the US?",
                "France",
                "England", "France", "Canada", "Spain");

        Question q5 = new Question("Which planet is closest to Earth?",
                "Venus",
                "Venus", "Mars", "Jupiter", "Mercury");

        this.addQuestions(q1, q2, q3, q4, q5);
    }

    public void loadQuestions() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.questions = Arrays.asList(
                    mapper.readValue(
                            Paths.get("src/main/java/com/booleanuk/questions.json").toFile(),
                            Question[].class
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
