package org.example;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

import static org.example.BorrowedData.borrowedRecords;

public class CSVReaderUtil {

    public static List<String[]> readCSV(String filePath) {
        //empty list data
        List<String[]> data = new ArrayList<>();
        String line;

        //attempts to open the folder
        try {
            //first searches for it locally
            System.out.println("Looking for file at: " + new File(filePath).getAbsolutePath());
            BufferedReader br;
            File file = new File(filePath);

            //checks if the file exists
            if (file.exists()) {
                //if it does it reads the file
                br = new BufferedReader(new FileReader(file));
            } else {
                // tries to load the file form the resources folder
                InputStream is = CSVReaderUtil.class.getResourceAsStream("/" + filePath);
                //if the file is not found anywhere it outputs file not found
                if (is == null) throw new IOException("File not found: " + filePath);
                br = new BufferedReader(new InputStreamReader(is));
            }

            br.readLine(); // Skip header

            //reads each line and splits it with ","
            while ((line = br.readLine()) != null) {
                //stores each row in a string
                data.add(line.split(","));
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error reading: " + filePath);
            e.printStackTrace();
        }

        return data;
    }
    // this lets you reload any csv file with one reusable function
    //generic method (<T>) created any type of object
    public static <T> List<T> loadCSV(String filePath, Function<String[], T> mapper) {
        // new empty list
        List<T> list = new ArrayList<>();
        // calls csv file and reads it
        for (String[] row : readCSV(filePath)) {
            try {
                // this makes the csv rows become objects in memory
                list.add(mapper.apply(row));
            } catch (Exception e) {
                System.out.println("Skipping invalid row: " + Arrays.toString(row));
            }
        }
        return list;
    }


    // 3. Specific Loaders (using generic mapper)
    public static List<User> loadUsers(String path) {
        return loadCSV(path, row -> new User(
                row[0], row[1], row[2], row[3], row[4], row[5]
        ));
    }

    public static List<Admin> loadAdmins(String path) {
        return loadCSV(path, row -> new Admin(
                row[0], row[1], row[2], row[3], row[4], row[5], row[6]
        ));
    }

    public static List<Librarian> loadLibrarians(String path) {
        return loadCSV(path, row -> new Librarian(
                row[0], row[1], row[2], row[3], row[4], row[5], row[6]
        ));
    }

    public static List<Student> loadStudents(String path) {
        return loadCSV(path, row -> new Student(
                row[0], row[1], row[2], row[3], row[4], row[5], row[6]
        ));
    }

    public static List<LibraryResources> loadLibraryResources(String path) {
        return loadCSV(path, row -> new LibraryResources(
                row[0], row[1], row[2], row[3], row[4], parseInt(row[5]), row[6]
        ));
    }

    public static List<Books> loadBooks(String path) {
        return loadCSV(path, row -> new Books(
                row[0], row[1], row[2], row[3], parseInt(row[4]), row[5]
        ));
    }

    // Journals loader
    public static List<Journals> loadJournals(String path) {
        return loadCSV(path, row -> new Journals(
                row[0],     // ItemID
                row[1],     // JournalID
                row[2],     // Category
                row[3],     // Genre
                parseInt(row[4]), // PageCount
                row[5]      // IssueNumber
        ));
    }

    //  Media loader
    public static List<Media> loadMedia(String path) {
        return loadCSV(path, row -> new Media(
                row[0],                // ItemID
                row[1],                // MediaID/Name
                row[2],                // Topic
                parseDouble(row[3]),   // FileSize
                row[4],                // Format
                parseInt(row[5])       // Duration
        ));
    }
    public static List<BorrowedRecord> loadBorrowedRecords(String path) {
        return loadCSV(path, row -> new BorrowedRecord(
                row[0],                     // userID
                row[1],                     // userName
                row[2],                     // itemID
                row[3],                     // itemName
                LocalDate.parse(row[4]),    // borrowDate
                LocalDate.parse(row[5]),    // dueDate
                Boolean.parseBoolean(row[6]), // extended
                Boolean.parseBoolean(row[7])  // returned  <-- ADD THIS
        ));
    }

    /*
     * Links child resource objects (Books, Journals, Media) with their corresponding
     * parent resource entry from LibraryResources.
     *
     * This method fills in inherited/common attributes (name, publisher, availability, etc.)
     * inside each child object by matching them with the correct parent entry using ItemID.
     *
     * @param children A list of child resource objects (Book, Journal, Media)
     * @param parents  The master list of LibraryResources (the parent table)
     * @param <T>      A type that extends LibraryResources (ensures type safety)
     */
    public static <T extends LibraryResources> void linkResourceData(
            List<T> children, List<LibraryResources> parents
    ) {
        // Loop through each child object (Book, Journal, Media)
        for (T child : children) {

            // For each child, scan the entire parent list for a matching ItemID
            for (LibraryResources parent : parents) {

                // If the ItemID of a child resource matches the parent's ItemID,
                // this means they represent the same real-world resource.
                if (child.getItemID().equals(parent.getItemID())) {

                    // Copy all shared fields from the parent resource into the child object.
                    child.setName(parent.getName());
                    child.setPublisher(parent.getPublisher());
                    child.setPublishDate(parent.getPublishDate());
                    child.setAvailability(parent.getAvailability());
                    child.setAmountAvailable(parent.getAmountAvailable());
                    child.setType(parent.getType());
                }
            }
        }
    }

    /*
     * Links child user objects (Admin, Librarian, Student) with their
     * corresponding record in the main Users list.
     *
     * This method copies all shared user attributes (first name, last name,
     * role, email, password) from the parent User object into the child object.
     *
     * @param children A list of child user objects (Admin, Librarian, Student)
     * @param parents  The main list of all User objects loaded from Users.csv
     * @param <T>      A type that extends User (ensures type safety)
     */
    public static <T extends User> void linkUserData(
            List<T> children, List<User> parents
    ) {
        // Loop through each child object (Admin, Librarian, Student)
        for (T child : children) {

            // For each child, look through ALL parent users
            for (User parent : parents) {

                // Match child & parent using their UserID (unique identifier)
                if (child.getUserID().equals(parent.getUserID())) {

                    // Copy shared attributes from the parent into the child
                    child.setFirstName(parent.getFirstName());
                    child.setLastName(parent.getLastName());
                    child.setUserRole(parent.getUserRole());
                    child.setEmail(parent.getEmail());
                    child.setPassword(parent.getPassword());
                }
            }
        }
    }


    public static void loadAllData() {

        //  1. Load All CSV Files Into Temporary Lists

        // Load the master user table (contains all user personal data)
        List<User> users = loadUsers("src/main/resources/Users.csv");

        // Load the main resource table (contains all resource parent info)
        List<LibraryResources> resources = loadLibraryResources("src/main/resources/LibraryResources.csv");

        // Load child user tables (contain role-specific fields only)
        List<Admin> admins = loadAdmins("src/main/resources/Admins.csv");
        List<Librarian> librarians = loadLibrarians("src/main/resources/Librarians.csv");
        List<Student> students = loadStudents("src/main/resources/Students.csv");

        // Load child resource tables (contain details specific to Books, Journals, Media)
        List<Books> books = loadBooks("src/main/resources/Books.csv");
        List<Journals> journals = loadJournals("src/main/resources/Journals.csv");
        List<Media> media = loadMedia("src/main/resources/Media.csv");


        // 2. Link Child Objects With Their Parent Object

        // Join Admins.csv & Users.csv using UserID
        linkUserData(admins, users);

        // Join Librarians.csv & Users.csv
        linkUserData(librarians, users);

        // Join Students.csv & Users.csv
        linkUserData(students, users);

        // Join Books.csv & LibraryResources.csv using ItemID
        linkResourceData(books, resources);

        // Join Journals.csv & LibraryResources.csv
        linkResourceData(journals, resources);

        // Join Media.csv & LibraryResources.csv
        linkResourceData(media, resources);


        // 3. Store All Linked Data Into Static Lists

        // These static lists are used throughout the whole LMS
        User.users = users;
        Admin.admins = admins;
        Librarian.librarians = librarians;
        Student.students = students;

        LibraryResources.resources = resources;
        Books.books = books;
        Journals.journals = journals;
        Media.mediaList = media;


        // 4. Load Borrowed Records
        BorrowedData.borrowedRecords =
                loadBorrowedRecords("src/main/resources/BorrowedRecords.csv");


        // 5. Sync Borrowed Items With User Objects

        // Rebuild each user's borrowedResources list from BorrowedRecords.csv
        BorrowedData.syncBorrowedRecordsToUsers();


        // 6. Clean Up Invalid Borrowed Records

        // Remove any borrowed record where the resource no longer exists
        BorrowedData.borrowedRecords.removeIf(record ->
                LibraryResources.resources.stream()
                        .noneMatch(r -> r.getItemID().equals(record.getItemID()))
        );
    }

    // 6. HELPERS
    private static int parseInt(String s) {
        try { return Integer.parseInt(s.trim()); }
        catch (Exception e) { return 0; }
    }

    private static double parseDouble(String s) {
        try { return Double.parseDouble(s.trim()); }
        catch (Exception e) { return 0.0; }
    }
}