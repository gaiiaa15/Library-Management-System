package org.example;

import java.util.Scanner;

 // Handles all authentication and access-routing functions for the LMS
 // This class does NOT store users – it only loads and validates them against the already-populated User.users list.
public class LoginManager {

    // Input reader for login interaction
    static Scanner scanner = new Scanner(System.in);

    //Stores the user currently logged into the system, If null then no one is logged in.
    public static User loggedInUser = null;

    /*
     * Handles the login process:
     *   - Prompts user for email/password
     *   - Iterates through all loaded users (User.users)
     *   - Validates credentials (case-insensitive email)
     *   - Stores the successfully logged-in user
     *   - Returns the authenticated user object
     *
     * If login fails:
     *   Allows retry
     *   Or exits the login loop
     */
    public static User login() {

        System.out.println("===== LMS LOGIN =====");

        while (true) {
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine().trim();

            // Search for matching user
            for (User user : User.users) {

                boolean emailMatch = user.getEmail().equalsIgnoreCase(email);
                boolean passwordMatch = user.getPassword().equals(password);

                if (emailMatch && passwordMatch) {

                    // Store logged-in user
                    loggedInUser = user;

                    System.out.println("Welcome, " +
                            user.getFirstName() + " " + user.getLastName());
                    System.out.println("Role: " + user.getUserRole());

                    return user;
                }
            }

            // Failed login attempt
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


     // Sends a successfully logged-in user to the correct dashboard:
     // If role is unrecognized, a warning is printed.
    public static void directUser(User user) {

        if (user == null)
            return; // login failed or user exited

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


     // Logs the current user out by clearing the loggedInUser field.
     // Also prints feedback to the console.
    public static void logout() {
        System.out.println("\n Logged out: " +
                (loggedInUser != null ? loggedInUser.getEmail() : "No user"));
        loggedInUser = null;
    }
}