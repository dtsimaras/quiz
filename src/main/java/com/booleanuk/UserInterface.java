package com.booleanuk;

import java.util.Scanner;

// TODO: refactor this class? Should this be a class?
public class UserInterface {
    // Description: this class was created in an effort to make quiz classs
    // independent of the UI, so possibly we can change from console to a different
    // way to communicate with user in the future, or in different implementations

    public static char getUserInput() {
        try {
            Scanner scanner = new Scanner(System.in);
            char userInput = scanner.nextLine().trim().toUpperCase().charAt(0);
            return userInput;
        } catch (Exception e) {
            e.printStackTrace();
            return 'N';
        }
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }
}
