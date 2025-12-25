package org.example;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.example.LoginManager.scanner;

public class StudentMenu {
    private static Scanner studentInput = new Scanner(System.in);
    public static void display() {
        boolean running = true;
        while(running) {
            System.out.println("\n===== STUDENT DASHBOARD =====");
            System.out.println("1. Search Resources");
            System.out.println("2. Borrow Resource");
            System.out.println("3. Return Resource");
            System.out.println("4. Extend Borrowing");
            System.out.println("5. View My Borrowed Items");
            System.out.println("6. Logout");

            String studentChoice = studentInput.nextLine();
            switch(studentChoice){
                case "1":
                    System.out.print("Enter name or keyword: ");
                    String keyword = studentInput.nextLine().toLowerCase();

// Filter the results first
                    List<LibraryResources> results = LibraryResources.resources.stream()
                            .filter(r -> r.getName().toLowerCase().contains(keyword))
                            .collect(Collectors.toList());

// If no results found
                    if (results.isEmpty()) {
                        System.out.println("\n⚠ No resources found for: " + keyword);
                        return;
                    }

// Print table header
                    System.out.println("\n=== SEARCH RESULTS ===");
                    System.out.println(LibraryResources.getTableHeader());

// Print each matched item in table row format
                    results.forEach(System.out::println);
                    break;

                case "2":
                    System.out.print("Enter Item ID to borrow: ");
                    String itemID = studentInput.nextLine();
                    BorrowManager.borrowItem(LoginManager.loggedInUser, itemID);
                    break;

                case "3":
                    System.out.print("Enter Item ID to return: ");
                    String returnID = studentInput.nextLine();
                    ReturnManager.returnItem(LoginManager.loggedInUser, returnID);
                    break;

                case "4":
                    System.out.print("Enter Item ID to extend: ");
                    ExtendManager.extendBorrow(LoginManager.loggedInUser, studentInput.nextLine());
                    break;

                case "5":
                    BorrowedData.showBorrowedForUser(LoginManager.loggedInUser);
                    break;

                case "6":
                    LoginManager.logout();
                    running = false;
                    break;


            }
        }
    }
}
