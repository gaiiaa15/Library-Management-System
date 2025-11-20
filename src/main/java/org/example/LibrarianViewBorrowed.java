package org.example;

import java.time.LocalDate;

public class LibrarianViewBorrowed {

    public static void showAllBorrowed() {
        System.out.println("\n================ BORROWED RESOURCES ================\n");
        System.out.printf("%-12s %-20s %-10s %-25s %-12s %-12s %-12s\n",
                "UserID", "User Name", "ItemID", "Title", "Borrowed", "Due Date", "Status");
        System.out.println("------------------------------------------------------------------------------------------");

        for (BorrowedRecord r : BorrowedData.borrowedRecords) {

            String status;
            if (r.isReturned()) {
                status = "Returned";
            } else if (r.getDueDate().isBefore(LocalDate.now())) {
                status = "OVERDUE";
            } else {
                status = "Borrowed";
            }

            System.out.printf("%-12s %-20s %-10s %-25s %-12s %-12s %-12s\n",
                    r.getUserID(),
                    r.getUserName(),
                    r.getItemID(),
                    r.getItemName(),
                    r.getBorrowDate(),
                    r.getDueDate(),
                    status
            );
        }

        System.out.println("====================================================\n");
    }
}