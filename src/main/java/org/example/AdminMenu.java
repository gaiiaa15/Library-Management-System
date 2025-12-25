package org.example;

import java.util.Scanner;

// this class has the admin dashboard and what each option from the dashboard does
public class AdminMenu {

    // reads the admin input
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


            //after reading the admin input it is now stored in adminChoice string
            String adminChoice = adminInput.nextLine();

            // switch that calls the methods and makes the dashboard functional
            switch(adminChoice){
                // view users
                case "1":
                    // calls getUserTableHeader from User class and outputs it
                    System.out.println(User.getUserTableHeader());
                    //loops through every user in the User.csv file and outputs it
                    User.users.forEach(System.out::println);
                    break;

                 // borrow resource
                case "2":
                    //asks the admin to enter the itemID of the item the want to borrow
                    System.out.print("Enter Item ID to borrow: ");
                    // stores the admin's input in itemID string
                    String itemID = adminInput.nextLine();
                    // calls the borrowing method for BorrowManager class
                    BorrowManager.borrowItem(LoginManager.loggedInUser, itemID);
                    break;

                    // extended borrowing
                case "3":
                    // asks for the itemID of the item they borrowed
                    System.out.print("Enter Item ID to extend: ");
                    // calls the extending loan method from ExtendManager class
                    ExtendManager.extendBorrow(LoginManager.loggedInUser, adminInput.nextLine());
                    break;

                    // return the resource
                case "4":
                    System.out.print("Enter Item ID to return: ");
                    // stores the admin input into returnID string
                    String returnID = adminInput.nextLine();
                    //calls the return method from the ReturnManager class
                    ReturnManager.returnItem(LoginManager.loggedInUser, returnID);
                    break;

                    // view my borrowed items
                case "5":
                    // class the method i which lists the borrowed items for the logged-in user
                    BorrowedData.showBorrowedForUser(LoginManager.loggedInUser);
                    break;

                    // view library resources
                case "6":
                    //prints out all the library resources
                    System.out.println(LibraryResources.getTableHeader());
                    LibraryResources.resources.forEach(System.out::println);
                    break;

                    // add new user
                case "7":
                    // calls the creat new user method from the AdminManager class
                    AdminManager.createNewUser();
                    break;


                case "8":
                    //logs user out
                    LoginManager.logout();
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }


}
