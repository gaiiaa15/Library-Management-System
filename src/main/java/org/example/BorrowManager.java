package org.example;

import java.time.LocalDate;

public class BorrowManager {

    public static boolean borrowItem(User user, String itemID) {

        // Normalize input
        String normalizedID = itemID.trim().toLowerCase();

        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusWeeks(2);  // 2-week borrow period

        // Find the resource from memory (case-insensitive)
        LibraryResources resource = LibraryResources.resources.stream()
                .filter(r -> r.getItemID().trim().equalsIgnoreCase(normalizedID))
                .findFirst()
                .orElse(null);

        if (resource == null) {
            System.out.println("Resource not found.");
            return false;
        }

        // User attempts to borrow
        boolean success = user.borrowResource(resource);

        if (success) {

            BorrowedRecord record = new BorrowedRecord(
                    user.getUserID(),
                    user.getFirstName() + " " + user.getLastName(),
                    resource.getItemID(),
                    resource.getName(),
                    LocalDate.now(),               // borrowDate
                    LocalDate.now().plusWeeks(1),  // dueDate
                    false,                         // extended
                    false                          // returned
            );

            // Add to in-memory list
            BorrowedData.borrowedRecords.add(record);

            // Save to CSV
            CSVWriterUtil.saveBorrowedRecords(
                    "src/main/resources/BorrowedRecords.csv",
                    BorrowedData.borrowedRecords
            );

            // Save updated LibraryResources CSV
            CSVWriterUtil.saveLibraryResources("src/main/resources/LibraryResources.csv");

            System.out.println("Borrowed successfully: " + resource.getName());
            System.out.println("Due date: " + record.getDueDate());
        }

        return success;
    }
}