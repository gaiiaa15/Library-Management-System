package org.example;

import java.util.ArrayList;
import java.util.List;


 // Parent class representing a generic library resource.
public class LibraryResources {


     // Global list storing ALL loaded library resources.
    public static List<LibraryResources> resources = new ArrayList<>();

// Shred Parent Attributes
    private String itemID;          // Unique resource identifier (e.g., I001)
    private String name;            // Title or resource name
    private String publisher;       // Publisher name
    private String publishDate;     // Year/Date published
    private String availability;    // Yes / No
    private int amountAvailable;    // Number of copies
    private String type;            // Book / Journal / Media

     // Empty constructor
    public LibraryResources() {}


     // Constructor used when child classes only know the ItemID initially.
     // (During CSV linking, children inherit additional details.)
    public LibraryResources(String itemID) {
        this.itemID = itemID;
    }


     // Full constructor for loading data directly from LibraryResources.csv.
    public LibraryResources(String itemID, String name, String publisher,
                            String publishDate, String availability,
                            int amountAvailable, String type) {

        this.itemID = itemID;
        this.name = name;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.availability = availability;
        this.amountAvailable = amountAvailable;
        this.type = type;
    }


    // GETTERS
    public String getItemID() { return itemID; }
    public String getName() { return name; }
    public String getPublisher() { return publisher; }
    public String getPublishDate() { return publishDate; }
    public String getAvailability() { return availability; }
    public int getAmountAvailable() { return amountAvailable; }
    public String getType() { return type; }


    // SETTERS
    public void setName(String name) { this.name = name; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setPublishDate(String publishDate) { this.publishDate = publishDate; }
    public void setAvailability(String availability) { this.availability = availability; }
    public void setAmountAvailable(int amountAvailable) { this.amountAvailable = amountAvailable; }
    public void setType(String type) { this.type = type; }




     // Generates a consistent table header when listing resources.

    public static String getTableHeader() {
        return String.format(
                "%-8s | %-30s | %-15s | %-12s | %-12s | %-5s | %-10s",
                "ItemID", "Name", "Publisher", "Published",
                "Availability", "Left", "Type"
        ) + "\n-----------------------------------------------------------------------------------------------";
    }


     // Clean row output matching the header formatting.
    @Override
    public String toString() {
        return String.format(
                "%-8s | %-30s | %-15s | %-12s | %-12s | %-5d | %-10s",
                itemID,
                name,
                publisher,
                publishDate,
                availability,
                amountAvailable,
                type
        );
    }
}