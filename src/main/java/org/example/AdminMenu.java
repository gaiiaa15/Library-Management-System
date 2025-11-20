package org.example;

import java.util.Scanner;

public class AdminMenu {

    private static Scanner adminInput = new Scanner(System.in);
    public static void display() {
        boolean running = true;
        while (running) {
            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("1. View Users");
            System.out.println("2. Borrow Resource");
            System.out.println("3. Extended Borrowing");
            System.out.println("4. Return Resource");
            System.out.println("5. View My Borrowed Items");
            System.out.println("6. View Library Resources");
            System.out.println("7. Add New User");
            System.out.println("8. Logout");

            String adminChoice = adminInput.nextLine();
            switch(adminChoice){
                case "1":
                    System.out.println(User.getUserTableHeader());
                    User.users.forEach(System.out::println);
                    break;

                case "2":
                    System.out.print("Enter Item ID to borrow: ");
                    String itemID = adminInput.nextLine();
                    BorrowManager.borrowItem(LoginManager.loggedInUser, itemID);
                    break;

                case "3":
                    System.out.print("Enter Item ID to extend: ");
                    ExtendManager.extendBorrow(LoginManager.loggedInUser, adminInput.nextLine());
                    break;

                case "4":
                    System.out.print("Enter Item ID to return: ");
                    String returnID = adminInput.nextLine();
                    ReturnManager.returnItem(LoginManager.loggedInUser, returnID);
                    break;

                case "5":
                    BorrowedData.showBorrowedForUser(LoginManager.loggedInUser);
                    break;


                case "6":
                    System.out.println(LibraryResources.getTableHeader());
                    LibraryResources.resources.forEach(System.out::println);
                    break;

                case "7":
                    AdminManager.createNewUser();
                    break;


                case "8":
                    System.out.println("Logging out...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }


}
