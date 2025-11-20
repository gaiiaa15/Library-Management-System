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

            if (file.exists()) {
                br = new BufferedReader(new FileReader(file));
            } else {
                InputStream is = CSVReaderUtil.class.getResourceAsStream("/" + filePath);
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
    public static <T> List<T> loadCSV(String filePath, Function<String[], T> mapper) {
        List<T> list = new ArrayList<>();
        for (String[] row : readCSV(filePath)) {
            try {
                list.add(mapper.apply(row));
            } catch (Exception e) {
                System.out.println("Skipping invalid row: " + Arrays.toString(row));
            }
        }
        return list;
    }


    // 3. SPECIFIC LOADERS (NOW USING GENERIC MAPPER)
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

    // 4. GENERIC LINKERS (USER + RESOURCES)
    public static <T extends LibraryResources> void linkResourceData(
            List<T> children, List<LibraryResources> parents
    ) {
        for (T child : children) {
            for (LibraryResources parent : parents) {
                if (child.getItemID().equals(parent.getItemID())) {
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

    public static <T extends User> void linkUserData(
            List<T> children, List<User> parents
    ) {
        for (T child : children) {
            for (User parent : parents) {
                if (child.getUserID().equals(parent.getUserID())) {
                    child.setFirstName(parent.getFirstName());
                    child.setLastName(parent.getLastName());
                    child.setUserRole(parent.getUserRole());
                    child.setEmail(parent.getEmail());
                    child.setPassword(parent.getPassword());
                }
            }
        }
    }


 // this calls all the other load methods and loads them in the correct order
    // links users to admins, students and librarians
    //links library resources to media, books and journals
    //loads borrowed records
    //syncs borrowed items into user objects
    public static void loadAllData() {

        // Load all CSVs
        List<User> users = loadUsers("src/main/resources/Users.csv");
        List<LibraryResources> resources = loadLibraryResources("src/main/resources/LibraryResources.csv");

        List<Admin> admins = loadAdmins("src/main/resources/Admins.csv");
        List<Librarian> librarians = loadLibrarians("src/main/resources/Librarians.csv");
        List<Student> students = loadStudents("src/main/resources/Students.csv");

        List<Books> books = loadBooks("src/main/resources/Books.csv");
        List<Journals> journals = loadJournals("src/main/resources/Journals.csv");
        List<Media> media = loadMedia("src/main/resources/Media.csv");


        // Parent → Child Linking
        linkUserData(admins, users);
        linkUserData(librarians, users);
        linkUserData(students, users);

        linkResourceData(books, resources);
        linkResourceData(journals, resources);
        linkResourceData(media, resources);

        // Store results in class static lists
        User.users = users;
        Admin.admins = admins;
        Librarian.librarians = librarians;
        Student.students = students;

        LibraryResources.resources = resources;
        Books.books = books;
        Journals.journals = journals;
        Media.mediaList = media;

        BorrowedData.borrowedRecords = loadBorrowedRecords("src/main/resources/BorrowedRecords.csv");

        BorrowedData.syncBorrowedRecordsToUsers();
        //validates the record recources ID
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