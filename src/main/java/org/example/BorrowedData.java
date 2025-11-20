package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//this class acts as a shared storage (memory) for all borrowed records in the system
// it is not a model class but a data container that is used by several other classes,
// BorrowManager, CSVReaderUtil, CSVWriterUtil, ReturnManager, User
public class BorrowedData {
    //single shared list that containing all borrowed records in the LMS
    public static List<BorrowedRecord> borrowedRecords = new ArrayList<>();

    //this rebuilds each User's list of borrowed item IDs after loading the LMS and CSV files.
    public static void syncBorrowedRecordsToUsers() {

        // Clear all users borrowed lists first
        for (User user : User.users) {
            user.getBorrowedResources().clear();
        }

        // Reassign borrowed items based on BorrowedRecord list
        for (BorrowedRecord record : borrowedRecords) {

            // Find the matching user
            User user = User.users.stream()
                    .filter(u -> u.getUserID().equals(record.getUserID()))
                    .findFirst()
                    .orElse(null);

            if (user != null) {
                user.getBorrowedResources().add(record.getItemID());
            }
        }

        System.out.println("Borrowed records synced to user objects.");
    }
    public static void showBorrowedForUser(User user) {
        System.out.println("\n===== YOUR BORROWED ITEMS =====");

        List<BorrowedRecord> myList = borrowedRecords.stream()
                .filter(r -> r.getUserID().equals(user.getUserID()))
                .collect(Collectors.toList());   // <-- FIXED

        if (myList.isEmpty()) {
            System.out.println("You have no borrowed items.");
            return;
        }

        for (BorrowedRecord r : myList) {
            System.out.println(
                    "\nItem: " + r.getItemName() +
                            "\nItem ID: " + r.getItemID() +
                            "\nBorrowed on: " + r.getBorrowDate() +
                            "\nDue on: " + r.getDueDate() +
                            "\nExtended: " + (r.isExtended() ? "Yes" : "No")
            );
            System.out.println("---------------------------");
        }
    }

}
