package com.booleanuk;

import java.util.Scanner;

// TODO: refactor this class? Should this be a class?
public class UserInterface {
    // @Description: this class was created in an effort to make quiz classs
    // independent of the UI, so possibly we can change from console to a different
    // way to communicate with user in the future, or in different implementations
    private static final Scanner scanner = new Scanner(System.in);

    public static char getUserInput() {
        return scanner.nextLine().trim().toUpperCase().charAt(0);
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }
}
