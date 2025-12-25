package org.example;

import java.util.Scanner;


 // This class handles the librarian dashboard menu.
 // It displays all available librarian options and routes the user
 // to the correct function based on input.
public class LibrarianMenu {

    // Scanner used to read librarian input
    private static Scanner librarianInput = new Scanner(System.in);


     // Displays the librarian dashboard and processes menu choices.
     // The loop continues until the librarian selects "Logout".

    public static void display() {

        boolean running = true;

        while (running) {

            // Display menu
            System.out.println("\n===== LIBRARIAN DASHBOARD =====");
            System.out.println("1. View All Resources");
            System.out.println("2. Borrow Resource");
            System.out.println("3. Extended Borrowing");
            System.out.println("4. View My Borrowed Items");
            System.out.println("5. View All Borrowed Items");
            System.out.println("6. Return Resource");
            System.out.println("7. Check Availability");
            System.out.println("8. Manage Stock");
            System.out.println("9. Logout");

            // Read menu choice
            String librarianChoice = librarianInput.nextLine();

            switch (librarianChoice) {

                // 1. Show all library resources in table format
                case "1":
                    System.out.println(LibraryResources.getTableHeader());
                    LibraryResources.resources.forEach(System.out::println);
                    break;

                // 2. Borrow an item
                case "2":
                    System.out.print("Enter Item ID to borrow: ");
                    String itemID = librarianInput.nextLine();
                    BorrowManager.borrowItem(LoginManager.loggedInUser, itemID);
                    break;

                // 3. Extend a borrowed item's due date
                case "3":
                    System.out.print("Enter Item ID to extend: ");
                    ExtendManager.extendBorrow(LoginManager.loggedInUser, librarianInput.nextLine());
                    break;

                // 4. Show borrowed items for this librarian only
                case "4":
                    BorrowedData.showBorrowedForUser(LoginManager.loggedInUser);
                    break;

                // 5. Show ALL borrow records in the system
                case "5":
                    LibrarianViewBorrowed.showAllBorrowed();
                    break;

                // 6. Return an item
                case "6":
                    System.out.print("Enter Item ID to return: ");
                    String returnID = librarianInput.nextLine();
                    ReturnManager.returnItem(LoginManager.loggedInUser, returnID);
                    break;

                // 7. Check availability of a single resource by ID
                case "7":
                    System.out.print("Enter Item ID: ");
                    String id = librarianInput.nextLine();

                    LibraryResources resource = LibraryResources.resources.stream()
                            .filter(r -> r.getItemID().equalsIgnoreCase(id))
                            .findFirst()
                            .orElse(null);

                    if (resource == null) {
                        System.out.println("Resource not found.");
                    } else {
                        System.out.println(resource);
                    }
                    break;

                // 8. Manage stock (add/remove/update resources)
                case "8":
                    StockManager.manageStock();
                    break;

                // 9. Logout
                case "9":
                    LoginManager.logout();
                    running = false;
                    break;

                // Invalid inputs
                default:
                    System.out.println("Invalid choice, try again.");

            }
        }
    }
}