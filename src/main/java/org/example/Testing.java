package org.example;

public class Testing {
    public static void runTests() {

        System.out.println("\n========== RUNNING AUTOMATED LIBRARIAN TESTS ==========\n");

        // 1. Load data
        System.out.println("Loading all LMS data...");
        CSVReaderUtil.loadAllData();
        System.out.println("Data loaded.\n");

        // 2. Get the first librarian account
        Librarian librarian = Librarian.librarians.get(0);
        LoginManager.loggedInUser = librarian;

        System.out.println("Logged in as: " + librarian.getFirstName() + " " + librarian.getLastName());

        // Predefined IDs
        String testItemID = "I001";     // EXISTING
        String stockTestID = "I002";    // EXISTING
        String testNewID = "I999";      // TEST RESOURCE (safe)

        // ---------------------------------------------------------
        System.out.println("\nTEST 1: View All Resources");
        try {
            System.out.println(LibraryResources.getTableHeader());
            LibraryResources.resources.forEach(System.out::println);
            System.out.println("PASS\n");
        } catch (Exception e) { System.out.println("FAIL\n"); }

        // ---------------------------------------------------------
        System.out.println("TEST 2: Borrow Resource (" + testItemID + ")");
        try {
            BorrowManager.borrowItem(librarian, testItemID);
            System.out.println("PASS\n");
        } catch (Exception e) { System.out.println("FAIL\n"); }

        // ---------------------------------------------------------
        System.out.println("TEST 3: Extend Borrow");
        try {
            ExtendManager.extendBorrow(librarian, testItemID);
            System.out.println("PASS\n");
        } catch (Exception e) { System.out.println("FAIL\n"); }

        // ---------------------------------------------------------
        System.out.println("TEST 4: View My Borrowed Items");
        try {
            BorrowedData.showBorrowedForUser(librarian);
            System.out.println("PASS\n");
        } catch (Exception e) { System.out.println("FAIL\n"); }

        // ---------------------------------------------------------
        System.out.println("TEST 5: View All Borrowed Items");
        try {
            LibrarianViewBorrowed.showAllBorrowed();
            System.out.println("PASS\n");
        } catch (Exception e) { System.out.println("FAIL\n"); }

        // ---------------------------------------------------------
        System.out.println("TEST 6: Return Borrowed Item");
        try {
            ReturnManager.returnItem(librarian, testItemID);
            System.out.println("PASS\n");
        } catch (Exception e) { System.out.println("FAIL\n"); }

        // ---------------------------------------------------------
        System.out.println("TEST 7: Check Availability for " + stockTestID);
        try {
            LibraryResources res = LibraryResources.resources.stream()
                    .filter(r -> r.getItemID().equalsIgnoreCase(stockTestID))
                    .findFirst()
                    .orElse(null);
            System.out.println(res);
            System.out.println("PASS\n");
        } catch (Exception e) { System.out.println("FAIL\n"); }

        // ---------------------------------------------------------
        // AUTOMATED STOCK MANAGER TESTS
        // ---------------------------------------------------------

        System.out.println("TEST 8A: Automated Add New Resource");

        try {
            // Add parent Resource
            LibraryResources newRes = new LibraryResources(
                    testNewID, "TEST BOOK AUTO", "Test Publisher", "2025", "Yes", 3, "Book"
            );
            LibraryResources.resources.add(newRes);

            // Add child Book
            Books testBook = new Books(
                    testNewID, "B999", "999-TEST", "Testing", 111, "Test Author"
            );
            Books.books.add(testBook);

            CSVWriterUtil.saveLibraryResources("src/main/resources/LibraryResources.csv");
            CSVWriterUtil.saveBooks("src/main/resources/Books.csv");

            System.out.println("PASS – Added test resource: " + testNewID + "\n");

        } catch (Exception e) { System.out.println("FAIL\n"); }


        // ---------------------------------------------------------
        System.out.println("TEST 8B: Automated Update Stock");

        try {
            LibraryResources r = LibraryResources.resources.stream()
                    .filter(x -> x.getItemID().equalsIgnoreCase(stockTestID))
                    .findFirst()
                    .orElse(null);

            if (r != null) {
                r.setAmountAvailable(r.getAmountAvailable() + 5);
                r.setAvailability("Yes");
            }

            CSVWriterUtil.saveLibraryResources("src/main/resources/LibraryResources.csv");

            System.out.println("PASS – Updated stock for: " + stockTestID + "\n");

        } catch (Exception e) { System.out.println("FAIL\n"); }


        // ---------------------------------------------------------
        System.out.println("TEST 8C: Automated Remove Resource");

        try {
            LibraryResources.resources.removeIf(r -> r.getItemID().equalsIgnoreCase(testNewID));
            Books.books.removeIf(b -> b.getItemID().equalsIgnoreCase(testNewID));
            BorrowedData.borrowedRecords.removeIf(br -> br.getItemID().equalsIgnoreCase(testNewID));

            CSVWriterUtil.saveLibraryResources("src/main/resources/LibraryResources.csv");
            CSVWriterUtil.saveBooks("src/main/resources/Books.csv");
            CSVWriterUtil.saveBorrowedRecords("src/main/resources/BorrowedRecords.csv",
                    BorrowedData.borrowedRecords);

            System.out.println("PASS – Removed test resource: " + testNewID + "\n");

        } catch (Exception e) { System.out.println("FAIL\n"); }


        // ---------------------------------------------------------
        System.out.println("TEST 9: Logout");
        try {
            LoginManager.loggedInUser = null;
            System.out.println("PASS\n");
        } catch (Exception e) { System.out.println("FAIL\n"); }


        System.out.println("========== END OF TESTS ==========\n");
    }
}
