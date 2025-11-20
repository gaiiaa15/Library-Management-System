package org.example;

//parent class for all the resources
public class LibraryResources {

    //this will store all the loaded resources from LibraryResources.csv
    public static java.util.List<LibraryResources> resources = new java.util.ArrayList<>();

    //parent attributes
    private String itemID;
    private String name;
    private String publisher;
    private String publishDate;
    private String availability;
    private int amountAvailable;
    private String type;

    // Empty constructor
    public LibraryResources() {}

    // Constructor used by children when loading CSV
    public LibraryResources(String itemID) {
        this.itemID = itemID;
    }

    // Full constructor (used when loading parent CSV)
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

    // Getters
    public String getItemID() { return itemID; }
    public String getName() { return name; }
    public String getPublisher() { return publisher; }
    public String getPublishDate() { return publishDate; }
    public String getAvailability() { return availability; }
    public int getAmountAvailable() { return amountAvailable; }
    public String getType() { return type; }

    //setters
    public void setName(String name) { this.name = name; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setPublishDate(String publishDate) { this.publishDate = publishDate; }
    public void setAvailability(String availability) { this.availability = availability; }
    public void setAmountAvailable(int amountAvailable) { this.amountAvailable = amountAvailable; }
    public void setType(String type) { this.type = type; }


    public static String getTableHeader() {
        return String.format(
                "%-8s | %-30s | %-15s | %-12s | %-12s | %-5s | %-10s",
                "ItemID",
                "Name",
                "Publisher",
                "Published",
                "Availability",
                "Left",
                "Type"
        ) +
                "\n-----------------------------------------------------------------------------------------------";
    }
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