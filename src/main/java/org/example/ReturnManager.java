package org.example;

public class ReturnManager {

    public static boolean returnItem(User user, String itemID) {

        // 1. Convert the entered itemID into a consistent lowercase/trimmed format
        String normalizedID = itemID.trim().toLowerCase();


        // 2. Search LibraryResources list and find a matching itemID (case-insensitive)
        LibraryResources resource = LibraryResources.resources.stream()
                .filter(r -> r.getItemID().trim().equalsIgnoreCase(normalizedID))
                .findFirst()
                .orElse(null);

        // If resource doesn't exist in the system → return cancelled
        if (resource == null) {
            System.out.println("Resource not found.");
            return false;
        }


        // 3. Check user's personal borrowed list for matching itemID
        boolean hasBorrowed = user.getBorrowedResources().stream()
                .anyMatch(id -> id.trim().equalsIgnoreCase(normalizedID));

        if (!hasBorrowed) {
            System.out.println("You did not borrow this resource.");
            return false;
        }


        // This reduces amountAvailable and updates User.borrowedResources
        boolean success = user.returnResource(resource);

        // If the return action was successful
        if (success) {

            // 5. Update the borrowed record entry
            // Locate the BorrowedRecord entry and mark it as returned
            for (BorrowedRecord rec : BorrowedData.borrowedRecords) {
                if (rec.getUserID().equals(user.getUserID()) &&
                        rec.getItemID().equalsIgnoreCase(itemID)) {

                    rec.setReturned(true);   // new status
                    break;                  // Stop after finding the correct record
                }
            }

            // 6. Save updated borrowed records to CSV File
            CSVWriterUtil.saveBorrowedRecords(
                    "src/main/resources/BorrowedRecords.csv",
                    BorrowedData.borrowedRecords
            );


            // 7. Updates amountAvailable and availability (Yes/No)
            CSVWriterUtil.saveLibraryResources("src/main/resources/LibraryResources.csv");

            System.out.println("Return completed and saved.");
        }

        // Return whether the return operation succeeded
        return success;
    }
}