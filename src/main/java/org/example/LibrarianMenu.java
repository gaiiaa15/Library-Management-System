package org.example;


import java.util.Scanner;

public class LibrarianMenu {
    private static Scanner librarianInput = new Scanner(System.in);
    public static void display() {
        boolean running = true;
        while(running) {
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

            String librarianChoice = librarianInput.nextLine();
            switch(librarianChoice){
                case "1":
                    System.out.println(LibraryResources.getTableHeader());
                    LibraryResources.resources.forEach(System.out::println);
                    break;

                case "2":
                    System.out.print("Enter Item ID to borrow: ");
                    String itemID = librarianInput.nextLine();
                    BorrowManager.borrowItem(LoginManager.loggedInUser, itemID);
                    break;

                case "3":
                    System.out.print("Enter Item ID to extend: ");
                    ExtendManager.extendBorrow(LoginManager.loggedInUser, librarianInput.nextLine());
                    break;

                case"4":
                    BorrowedData.showBorrowedForUser(LoginManager.loggedInUser);
                    break;

                case "5":
                    LibrarianViewBorrowed.showAllBorrowed();
                    break;

                case "6":
                    System.out.print("Enter Item ID to return: ");
                    String returnID = librarianInput.nextLine();
                    ReturnManager.returnItem(LoginManager.loggedInUser, returnID);
                    break;

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

                case "8":
                    StockManager.manageStock();
                    break;

                case "9":
                    System.out.println("Logging out...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice, try again.");

            }
        }
    }
}
