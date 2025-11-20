package org.example;
import java.time.LocalDate;
import java.util.List;


public class BorrowedRecord {

    private String userID;
    private String userName;
    private String itemID;
    private String itemName;

    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean extended;
    private boolean returned;
    public BorrowedRecord(String userID, String userName, String itemID, String itemName,
                          LocalDate borrowDate, LocalDate dueDate, boolean extended, boolean returned) {
        this.userID = userID;
        this.userName = userName;
        this.itemID = itemID;
        this.itemName = itemName;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.extended = extended;
        this.returned = returned;
    }


    // Getters
    public String getUserID() { return userID; }
    public String getItemID() { return itemID; }
    public String getItemName() { return itemName; }
    public String getUserName() { return userName; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isExtended() { return extended; }


    public boolean isReturned() {
        return returned;
    }

    public void extendDueDate() {
        this.dueDate = this.dueDate.plusWeeks(1);
        this.extended = true;
    }

    public void setReturned(boolean returned) { this.returned = returned; }


    @Override
    public String toString() {
        return userID + "," + userName + "," + itemID + "," + itemName + "," +
                borrowDate + "," + dueDate + "," + extended + "," + returned;
    }
}