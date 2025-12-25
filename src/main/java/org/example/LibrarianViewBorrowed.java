package org.example;

import java.time.LocalDate;


 // This class allows librarians to view ALL borrowed records in the system.
 // It displays every active or returned loan in a formatted table.

public class LibrarianViewBorrowed {


     // Displays a table of all borrowed records
    public static void showAllBorrowed() {

        // Table title
        System.out.println("\n================ BORROWED RESOURCES ================\n");

        // Table header with column alignment
        System.out.printf(
                "%-12s %-20s %-10s %-25s %-12s %-12s %-12s\n",
                "UserID", "User Name", "ItemID", "Title",
                "Borrowed", "Due Date", "Status"
        );

        // Divider line
        System.out.println("------------------------------------------------------------------------------------------");

        // Iterate through all borrowed records stored in memory
        for (BorrowedRecord r : BorrowedData.borrowedRecords) {

            // Determine the status of each borrowed item
            String status;

            if (r.isReturned()) {
                status = "Returned";
            }
            else if (r.getDueDate().isBefore(LocalDate.now())) {
                status = "OVERDUE";
            }
            else {
                status = "Borrowed";
            }

            // Print each row in the table with aligned columns
            System.out.printf(
                    "%-12s %-20s %-10s %-25s %-12s %-12s %-12s\n",
                    r.getUserID(),
                    r.getUserName(),
                    r.getItemID(),
                    r.getItemName(),
                    r.getBorrowDate(),
                    r.getDueDate(),
                    status
            );
        }

        // End of table
        System.out.println("===========================================================================================\n");
    }
}