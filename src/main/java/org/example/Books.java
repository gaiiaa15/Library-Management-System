package org.example;

import java.util.ArrayList;
import java.util.List;

public class Books extends LibraryResources {


    // Static storage for all books
    public static List<Books> books = new ArrayList<>();

    // Child-specific attributes
    private String bookID;
    private String isbn;
    private String genre;
    private int pageCount;
    private String authorName;


    // Empty Constructor (for default use)
    public Books() {
        super();
    }


    // Constructor used when loading Books.csv
    //  Parent fields are NOT filled here.
    // They will be linked later using linkResourceData()
    public Books(String itemID, String bookID, String isbn, String genre, int pageCount, String authorName) {
        // Only pass the itemID to parent (everything else is filled during linking)
        super(itemID);
        this.bookID = bookID;
        this.isbn = isbn;
        this.genre = genre;
        this.pageCount = pageCount;
        this.authorName = authorName;
    }

    // Getters
    public String getBookID() { return bookID; }
    public String getIsbn() { return isbn; }
    public String getGenre() { return genre; }
    public int getPageCount() { return pageCount; }
    public String getAuthorName() { return authorName; }



    // Printable Output
    @Override
    public String toString() {
        return "Book{" +
                "ItemID='" + getItemID() + '\'' +
                ", Name='" + getName() + '\'' +
                ", Publisher='" + getPublisher() + '\'' +
                ", PublishDate='" + getPublishDate() + '\'' +
                ", Availability='" + getAvailability() + '\'' +
                ", AmountAvailable=" + getAmountAvailable() +
                ", Type='" + getType() + '\'' +
                ", BookID='" + bookID + '\'' +
                ", ISBN='" + isbn + '\'' +
                ", Genre='" + genre + '\'' +
                ", PageCount=" + pageCount +
                ", AuthorName='" + authorName + '\'' +
                '}';
    }
}