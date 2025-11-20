package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Load & link data
        //CSVReaderUtil.loadAllData();
//        // TEMP: Test printing all borrowed records
//        System.out.println("=== CURRENT BORROWED RECORDS IN MEMORY ===");
//        BorrowedData.borrowedRecords.forEach(System.out::println);
//        System.out.println("=== END BORROWED RECORDS ===");

        // Start login process
       // User loggedInUser = LoginManager.login();
        // Redirect based on role
        //LoginManager.directUser(loggedInUser);

        // Run librarian automated tests
        Testing.runTests();

    }
}