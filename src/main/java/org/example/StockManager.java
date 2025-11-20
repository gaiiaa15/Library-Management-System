package org.example;


import java.util.Scanner;

public class StockManager {

    private static final Scanner scanner = new Scanner(System.in);

    public static void manageStock() {
        boolean running = true;

        while (running) {
            System.out.println("\n===== MANAGE LIBRARY STOCK =====");
            System.out.println("1. Add New Resource");
            System.out.println("2. Update Existing Resource Stock");
            System.out.println("3. Remove Resource");
            System.out.println("4. Back to Dashboard");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addNewResource();
                    break;
                case "2":
                    updateStock();
                    break;
                case "3":
                    removeResource();
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // =============== 1. ADD NEW RESOURCE ===============
    private static void addNewResource() {
        System.out.println("\n=== ADD NEW RESOURCE ===");
        System.out.println("Select resource type:");
        System.out.println("1. Book");
        System.out.println("2. Journal");
        System.out.println("3. Media");
        System.out.print("Enter choice: ");

        String typeChoice = scanner.nextLine().trim();
        String type;

        switch (typeChoice) {
            case "1":
                type = "Book";
                break;
            case "2":
                type = "Journal";
                break;
            case "3":
                type = "Media";
                break;
            default:
                System.out.println("Invalid type.");
                return;
        }

        String itemID = generateNextItemId();
        System.out.println("Generated ItemID: " + itemID);

        System.out.print("Title / Name: ");
        String name = scanner.nextLine();

        System.out.print("Publisher: ");
        String publisher = scanner.nextLine();

        System.out.print("Publish Year (e.g. 2021): ");
        String publishDate = scanner.nextLine();

        int amountAvailable = readInt("Amount available (number of copies): ");
        String availability = amountAvailable > 0 ? "Yes" : "No";

        // Create parent resource
        LibraryResources resource = new LibraryResources(
                itemID, name, publisher, publishDate, availability, amountAvailable, type
        );
        LibraryResources.resources.add(resource);

        // Create specific child
        switch (type) {
            case "Book":
                addBookDetails(itemID);
                break;
            case "Journal":
                addJournalDetails(itemID);
                break;
            case "Media":
                addMediaDetails(itemID);
                break;
        }

        // Save to CSVs
        CSVWriterUtil.saveLibraryResources("src/main/resources/LibraryResources.csv");
        CSVWriterUtil.saveBooks("src/main/resources/Books.csv");
        CSVWriterUtil.saveJournals("src/main/resources/Journals.csv");
        CSVWriterUtil.saveMedia("src/main/resources/Media.csv");

        System.out.println("New " + type + " added successfully.");
    }

    private static void addBookDetails(String itemID) {
        String bookID = generateNextBookId();
        System.out.println("Generated BookID: " + bookID);

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        int pageCount = readInt("Page count: ");

        System.out.print("Author name: ");
        String authorName = scanner.nextLine();

        Books book = new Books(itemID, bookID, isbn, genre, pageCount, authorName);
        Books.books.add(book);
    }

    private static void addJournalDetails(String itemID) {
        String journalID = generateNextJournalId();
        System.out.println("Generated JournalID: " + journalID);

        System.out.print("Category (Magazine, Newspaper, Article, etc.): ");
        String category = scanner.nextLine();

        System.out.print("Genre / Topic: ");
        String genre = scanner.nextLine();

        int pageCount = readInt("Page count: ");

        System.out.print("Issue number: ");
        String issueNumber = scanner.nextLine();

        Journals journal = new Journals(itemID, journalID, category, genre, pageCount, issueNumber);
        Journals.journals.add(journal);
    }

    private static void addMediaDetails(String itemID) {
        String mediaID = generateNextMediaId();
        System.out.println("Generated MediaID: " + mediaID);

        System.out.print("Topic / Description: ");
        String topic = scanner.nextLine();

        double fileSize = readDouble("File size (MB): ");

        System.out.print("Format (Audio / Video): ");
        String format = scanner.nextLine();

        int duration = readInt("Duration (minutes): ");

        Media media = new Media(itemID, mediaID, topic, fileSize, format, duration);
        Media.mediaList.add(media);
    }

    // =============== 2. UPDATE STOCK ===============
    private static void updateStock() {
        System.out.println("\n=== UPDATE RESOURCE STOCK ===");
        System.out.print("Enter Item ID: ");
        String itemID = scanner.nextLine().trim();

        LibraryResources resource = LibraryResources.resources.stream()
                .filter(r -> r.getItemID().equalsIgnoreCase(itemID))
                .findFirst()
                .orElse(null);

        if (resource == null) {
            System.out.println("Resource not found.");
            return;
        }

        System.out.println("\nCurrent resource:");
        System.out.println(resource.toString());

        int newAmount = readInt("Enter NEW total copies (0 or more): ");

        if (newAmount < 0) {
            System.out.println("Amount cannot be negative.");
            return;
        }

        resource.setAmountAvailable(newAmount);
        resource.setAvailability(newAmount > 0 ? "Yes" : "No");

        CSVWriterUtil.saveLibraryResources("src/main/resources/LibraryResources.csv");

        System.out.println("Stock updated successfully.");
    }

    // =============== 3. REMOVE RESOURCE ===============
    private static void removeResource() {
        System.out.println("\n=== REMOVE RESOURCE ===");
        System.out.print("Enter Item ID: ");
        String itemID = scanner.nextLine().trim();

        LibraryResources resource = LibraryResources.resources.stream()
                .filter(r -> r.getItemID().equalsIgnoreCase(itemID))
                .findFirst()
                .orElse(null);

        if (resource == null) {
            System.out.println("Resource not found.");
            return;
        }

        System.out.println("\nYou are about to DELETE:");
        System.out.println(resource.toString());
        System.out.print("Are you sure? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!confirm.equals("y")) {
            System.out.println("Delete cancelled.");
            return;
        }

        // Remove from parent list
        LibraryResources.resources.removeIf(r -> r.getItemID().equalsIgnoreCase(itemID));

        // Remove from specific child list based on type
        String type = resource.getType();
        if ("Book".equalsIgnoreCase(type)) {
            Books.books.removeIf(b -> b.getItemID().equalsIgnoreCase(itemID));
        } else if ("Journal".equalsIgnoreCase(type)) {
            Journals.journals.removeIf(j -> j.getItemID().equalsIgnoreCase(itemID));
        } else if ("Media".equalsIgnoreCase(type)) {
            Media.mediaList.removeIf(m -> m.getItemID().equalsIgnoreCase(itemID));
        }

        // Remove any borrow records for this item
        BorrowedData.borrowedRecords.removeIf(
                br -> br.getItemID().equalsIgnoreCase(itemID)
        );

        // Save all CSVs
        CSVWriterUtil.saveLibraryResources("src/main/resources/LibraryResources.csv");
        CSVWriterUtil.saveBooks("src/main/resources/Books.csv");
        CSVWriterUtil.saveJournals("src/main/resources/Journal.csv");
        CSVWriterUtil.saveMedia("src/main/resources/Media.csv");
        CSVWriterUtil.saveBorrowedRecords("src/main/resources/BorrowedRecords.csv", BorrowedData.borrowedRecords);

        System.out.println("Resource removed successfully.");
    }

    // =============== ID GENERATORS ===============
    private static String generateNextItemId() {
        int max = 0;
        for (LibraryResources r : LibraryResources.resources) {
            String id = r.getItemID(); // e.g., I032
            if (id != null && id.length() > 1 && Character.toUpperCase(id.charAt(0)) == 'I') {
                try {
                    int n = Integer.parseInt(id.substring(1));
                    if (n > max) max = n;
                } catch (NumberFormatException ignored) {}
            }
        }
        int next = max + 1;
        return String.format("I%03d", next);
    }

    private static String generateNextBookId() {
        int max = 0;
        for (Books b : Books.books) {
            String id = b.getBookID(); // e.g., B012
            if (id != null && id.length() > 1 && Character.toUpperCase(id.charAt(0)) == 'B') {
                try {
                    int n = Integer.parseInt(id.substring(1));
                    if (n > max) max = n;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("B%03d", max + 1);
    }

    private static String generateNextJournalId() {
        int max = 0;
        for (Journals j : Journals.journals) {
            String id = j.getJournalID(); // e.g., J005
            if (id != null && id.length() > 1 && Character.toUpperCase(id.charAt(0)) == 'J') {
                try {
                    int n = Integer.parseInt(id.substring(1));
                    if (n > max) max = n;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("J%03d", max + 1);
    }

    private static String generateNextMediaId() {
        int max = 0;
        for (Media m : Media.mediaList) {
            String id = m.getMediaID(); // e.g., M010
            if (id != null && id.length() > 1 && Character.toUpperCase(id.charAt(0)) == 'M') {
                try {
                    int n = Integer.parseInt(id.substring(1));
                    if (n > max) max = n;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("M%03d", max + 1);
    }

    // =============== INPUT HELPERS ===============
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid decimal number.");
            }
        }
    }
}
