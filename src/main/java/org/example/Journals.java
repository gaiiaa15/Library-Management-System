package org.example;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents a Journal resource (magazine, newspaper, article, etc.)
 * This is a CHILD class of LibraryResources.
 *
 * Journals.csv contains:
 * ItemID, JournalID, Category, Genre, PageCount, IssueNumber
 */
public class Journals extends LibraryResources {

     // Stores all Journal objects loaded from Journals.csv
     // This acts like an in-memory table for all journals.

    public static List<Journals> journals = new ArrayList<>();


    private String journalID;       // Unique ID for journal (e.g., J001)
    private String category;        // Magazine, Newspaper, Article, etc.
    private String genre;           // Subject category (Technology, Finance…)
    private int pageCount;          // Number of pages
    private String issueNumber;     // Issue reference (e.g., Issue 10)



     // Empty constructor — required for reflection or future use.
    public Journals() {
        super();
    }


     // Constructor used when loading from Journals.csv.
     // @param itemID       Parent reference ID (shared with LibraryResources)
     // @param journalID    Unique journal identifier
     // @param category     Category (Magazine, Newspaper…)
     // @param genre        Topic/genre
     // @param pageCount    Number of pages
     // @param issueNumber  Issue number

    public Journals(String itemID, String journalID, String category,
                    String genre, int pageCount, String issueNumber) {

        // Calls the parent constructor, setting only the itemID for now
        super(itemID);

        this.journalID = journalID;
        this.category = category;
        this.genre = genre;
        this.pageCount = pageCount;
        this.issueNumber = issueNumber;
    }



    // GETTERS & SETTERS
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





      //Returns a readable representation of a Journal including:
      // - parent fields (name, publisher, etc.)
     // - journal-specific fields (category, genre, issue number…)

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