package org.example;

public class ReturnManager {

    public static boolean returnItem(User user, String itemID) {

        // Normalize input
        String normalizedID = itemID.trim().toLowerCase();

        // 1. Find the resource (case-insensitive)
        LibraryResources resource = LibraryResources.resources.stream()
                .filter(r -> r.getItemID().trim().equalsIgnoreCase(normalizedID))
                .findFirst()
                .orElse(null);

        if (resource == null) {
            System.out.println("Resource not found.");
            return false;
        }

        // 2. Check if user actually borrowed it (case-insensitive)
        boolean hasBorrowed = user.getBorrowedResources().stream()
                .anyMatch(id -> id.trim().equalsIgnoreCase(normalizedID));

        if (!hasBorrowed) {
            System.out.println("You did not borrow this resource.");
            return false;
        }

        // 3. Perform user return logic
        boolean success = user.returnResource(resource);

        if (success) {

            for (BorrowedRecord rec : BorrowedData.borrowedRecords) {
                if (rec.getUserID().equals(user.getUserID()) &&
                        rec.getItemID().equalsIgnoreCase(itemID)) {

                    rec.setReturned(true);   // NEW FLAG
                    break;
                }
            }

            // 5. Save updated borrow records
            CSVWriterUtil.saveBorrowedRecords(
                    "src/main/resources/BorrowedRecords.csv",
                    BorrowedData.borrowedRecords
            );

            // 6. Save updated resource stock
            CSVWriterUtil.saveLibraryResources("src/main/resources/LibraryResources.csv");

            System.out.println("Return completed and saved.");
        }

        return success;
    }
}