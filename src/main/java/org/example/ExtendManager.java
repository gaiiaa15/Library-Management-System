package org.example;

public class ExtendManager {

    public static void extendBorrow(User user, String itemID) {

        BorrowedRecord record = BorrowedData.borrowedRecords.stream()
                .filter(r -> r.getUserID().equals(user.getUserID()) &&
                        r.getItemID().equals(itemID))
                .findFirst()
                .orElse(null);

        if (record == null) {
            System.out.println("No borrowing record found for this item.");
            return;
        }

        if (record.isExtended()) {
            System.out.println("You have already extended this borrow once.");
            return;
        }

        record.extendDueDate();
        CSVWriterUtil.saveBorrowedRecords("src/main/resources/BorrowedRecords.csv", BorrowedData.borrowedRecords);

        System.out.println("Borrow extended. New due date: " + record.getDueDate());
    }
}
