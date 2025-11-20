package org.example;

import java.util.Scanner;

public class LoginManager {

    static Scanner scanner = new Scanner(System.in);

    //Stores the user who is currently logged in
    public static User loggedInUser = null;

    // MAIN LOGIN METHOD
    public static User login() {

        System.out.println("===== LMS LOGIN =====");

        while (true) {
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine().trim();

            // Search all loaded users
            for (User user : User.users) {
                if (user.getEmail().equalsIgnoreCase(email)
                        && user.getPassword().equals(password)) {

                    loggedInUser = user; //store logged-in user

                    System.out.println("\nLogin Successful!");
                    System.out.println("Welcome, " + user.getFirstName() + " " + user.getLastName());
                    System.out.println("Role: " + user.getUserRole());
                    return user;
                }
            }

            // Failed login
            System.out.println("\nIncorrect email or password.");
            System.out.println("1. Try Again");
            System.out.println("2. Exit");

            String choice = scanner.nextLine().trim();
            if (choice.equals("2")) {
                System.out.println("Exiting login...");
                return null;
            }
        }
    }

    // ROLE ROUTING AFTER LOGIN
    public static void directUser(User user) {

        if (user == null) return;

        // Route based on role
        switch (user.getUserRole().toLowerCase()) {

            case "admin":
                AdminMenu.display();
                break;

            case "librarian":
                LibrarianMenu.display();
                break;

            case "student":
                StudentMenu.display();
                break;

            default:
                System.out.println("Unknown role: " + user.getUserRole());
        }
    }

    // LOGOUT
    public static void logout() {
        System.out.println("\n Logged out: " +
                (loggedInUser != null ? loggedInUser.getEmail() : "No user"));
        loggedInUser = null;
    }
}