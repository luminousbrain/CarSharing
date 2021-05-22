package ru.gontarenko.carsharing.util;

import java.util.Scanner;

public final class UserInput {
    public static final Scanner scanner = new Scanner(System.in);

    public static String getChoiceAsString() {
        return scanner.nextLine();
    }

    public static int getChoiceAsInteger() {
        int userInput;
        while (true) {
            try {
                userInput = Integer.parseInt(scanner.nextLine());
                if (userInput < 0) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong Input. Try again:");
            }
        }
        return userInput;
    }
}
