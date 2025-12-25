package org.example;

public class ExtendManager {

    /*
     * Attempts to extend the borrowing period for a specific item
     * for the given user.
     *
     * Rules:
     *  - User must have a BorrowedRecord for that item
     *  - They can only extend ONCE (record.isExtended() must be false)
     */
    public static void extendBorrow(User user, String itemID) {

        // Find the matching BorrowedRecord from the global borrowedRecords list
        // We look for a record where:
        //  - The UserID matches the logged-in user
        //  - The ItemID matches the item they want to extend
        BorrowedRecord record = BorrowedData.borrowedRecords.stream()
                .filter(r -> r.getUserID().equals(user.getUserID()) &&
                        r.getItemID().equals(itemID))
                .findFirst()
                .orElse(null);

        // If no such record exists, the user never borrowed this item
        if (record == null) {
            System.out.println("No borrowing record found for this item.");
            return;
        }

        // If the "extended" flag is already true, they have already extended once
        if (record.isExtended()) {
            System.out.println("You have already extended this borrow once.");
            return;
        }

        // Extend the due date by 1 week (handled inside BorrowedRecord.extendDueDate())
        // and mark the record as extended = true
        record.extendDueDate();

        // Persist the updated records list back to BorrowedRecords.csv
        CSVWriterUtil.saveBorrowedRecords(
                "src/main/resources/BorrowedRecords.csv",
                BorrowedData.borrowedRecords
        );

        // Inform the user of the new due date
        System.out.println("Borrow extended. New due date: " + record.getDueDate());
    }
}
