package org.example;

import java.util.ArrayList;
import java.util.List;

public class Journals extends LibraryResources {

    // Static storage list
    public static List<Journals> journals = new ArrayList<>();

    // Child-specific fields
    private String journalID;
    private String category;
    private String genre;
    private int pageCount;
    private String issueNumber;

    // Empty constructor (required)
    public Journals() {
        super();
    }

    // Constructor for loading Journals.csv (6 columns)
    public Journals(String itemID, String journalID, String category,
                    String genre, int pageCount, String issueNumber) {

        super(itemID);  // Only itemID is known here

        this.journalID = journalID;
        this.category = category;
        this.genre = genre;
        this.pageCount = pageCount;
        this.issueNumber = issueNumber;
    }

    // Getters and setters
    public String getJournalID() { return journalID; }
    public void setJournalID(String journalID) { this.journalID = journalID; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getPageCount() { return pageCount; }
    public void setPageCount(int pageCount) { this.pageCount = pageCount; }

    public String getIssueNumber() { return issueNumber; }
    public void setIssueNumber(String issueNumber) { this.issueNumber = issueNumber; }

    @Override
    public String toString() {
        return "Journal{" +
                "ItemID='" + getItemID() + '\'' +
                ", Name='" + getName() + '\'' +
                ", Publisher='" + getPublisher() + '\'' +
                ", PublishDate='" + getPublishDate() + '\'' +
                ", Availability='" + getAvailability() + '\'' +
                ", AmountAvailable=" + getAmountAvailable() +
                ", Type='" + getType() + '\'' +
                ", JournalID='" + journalID + '\'' +
                ", Category='" + category + '\'' +
                ", Genre='" + genre + '\'' +
                ", PageCount=" + pageCount +
                ", IssueNumber='" + issueNumber + '\'' +
                '}';
    }
}