package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/*
 * Utility class responsible for writing all LMS data back to CSV files.
 *
 * This class saves:
 *  - Library resources (parent table)
 *  - Borrowed records
 *  - Users (Admins, Librarians, Students)
 *  - Resource type details (Books, Journals, Media)
 *
 * Each save method rewrites the entire CSV file in a clean, structured manner.
 */
public class CSVWriterUtil {


     // Saves the entire LibraryResources list back to a CSV file.
     // This updates copies, availability, and names after stock changes or borrow/return actions.

    public static void saveLibraryResources(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            // Write column headers
            writer.write("ItemID,Name,Publisher,PublishDate,Availability,AmountAvailable,Type\n");

            // Write each resource as one row
            for (LibraryResources r : LibraryResources.resources) {
                writer.write(
                        r.getItemID() + "," +
                                r.getName() + "," +
                                r.getPublisher() + "," +
                                r.getPublishDate() + "," +
                                r.getAvailability() + "," +
                                r.getAmountAvailable() + "," +
                                r.getType() + "\n"
                );
            }

            System.out.println("LibraryResources saved");

        } catch (IOException e) {
            System.out.println("Error saving LibraryResources");
            e.printStackTrace();
        }
    }



     // Saves all borrowing records to the BorrowedRecords.csv file.
     // This includes due dates, extended flag, and user/item details.
    public static void saveBorrowedRecords(String filePath, List<BorrowedRecord> list) {

        System.out.println("Saving borrowed records to: " + new File(filePath).getAbsolutePath());

        try (FileWriter writer = new FileWriter(filePath)) {

            // Write CSV header
            writer.write("UserID,UserName,ItemID,ItemName,BorrowDate,DueDate,Extended\n");

            // Save each BorrowedRecord object
            for (BorrowedRecord record : list) {
                writer.write(
                        record.getUserID() + "," +
                                record.getUserName() + "," +
                                record.getItemID() + "," +
                                record.getItemName() + "," +
                                record.getBorrowDate() + "," +
                                record.getDueDate() + "," +
                                record.isExtended() + "\n"
                );
            }

            System.out.println("Borrowed records saved.");

        } catch (IOException e) {
            System.out.println("Error saving borrowed records");
            e.printStackTrace();
        }
    }



     //Saves all users in the system (parent table User.csv).

    public static void saveUsers(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.write("UserID,FirstName,LastName,UserRole,Email,Password\n");

            for (User u : User.users) {
                writer.write(
                        u.getUserID() + "," +
                                u.getFirstName() + "," +
                                u.getLastName() + "," +
                                u.getUserRole() + "," +
                                u.getEmail() + "," +
                                u.getPassword() + "\n"
                );
            }

            System.out.println("Users saved.");

        } catch (Exception e) {
            System.out.println("Error saving users");
        }
    }



      //Saves Admin-specific details to Admins.csv.
    //Includes AdminID which is unique to Administrator accounts.

    public static void saveAdmins(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.write("UserID,FirstName,LastName,UserRole,Email,Password,AdminID\n");

            for (Admin a : Admin.admins) {
                writer.write(
                        a.getUserID() + "," +
                                a.getFirstName() + "," +
                                a.getLastName() + "," +
                                a.getUserRole() + "," +
                                a.getEmail() + "," +
                                a.getPassword() + "," +
                                a.getAdminID() + "\n"
                );
            }

            System.out.println("Admins saved.");

        } catch (IOException e) {
            System.out.println("Error saving Admins");
            e.printStackTrace();
        }
    }



     //Saves Librarian-specific details including StaffID.

    public static void saveLibrarians(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.write("UserID,FirstName,LastName,UserRole,Email,Password,StaffID\n");

            for (Librarian l : Librarian.librarians) {
                writer.write(
                        l.getUserID() + "," +
                                l.getFirstName() + "," +
                                l.getLastName() + "," +
                                l.getUserRole() + "," +
                                l.getEmail() + "," +
                                l.getPassword() + "," +
                                l.getStaffID() + "\n"
                );
            }

            System.out.println("Librarians saved.");

        } catch (IOException e) {
            System.out.println("Error saving Librarians");
            e.printStackTrace();
        }
    }



    //Saves Student-specific details including StudentID.

    public static void saveStudents(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.write("UserID,FirstName,LastName,UserRole,Email,Password,StudentID\n");

            for (Student s : Student.students) {
                writer.write(
                        s.getUserID() + "," +
                                s.getFirstName() + "," +
                                s.getLastName() + "," +
                                s.getUserRole() + "," +
                                s.getEmail() + "," +
                                s.getPassword() + "," +
                                s.getStudentID() + "\n"
                );
            }

            System.out.println("Students saved.");

        } catch (IOException e) {
            System.out.println("Error saving Students");
            e.printStackTrace();
        }
    }



     //Saves detailed Book information.
    public static void saveBooks(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.write("ItemID,BookID,ISBN,Genre,PageCount,AuthorName\n");

            for (Books b : Books.books) {
                writer.write(
                        b.getItemID() + "," +
                                b.getBookID() + "," +
                                b.getIsbn() + "," +
                                b.getGenre() + "," +
                                b.getPageCount() + "," +
                                b.getAuthorName() + "\n"
                );
            }

            System.out.println("Books saved.");
        } catch (IOException e) {
            System.out.println("Error saving Books");
            e.printStackTrace();
        }
    }



    //  Saves detailed Journal information.

    public static void saveJournals(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.write("ItemID,JournalID,Category,Genre,PageCount,IssueNumber\n");

            for (Journals j : Journals.journals) {
                writer.write(
                        j.getItemID() + "," +
                                j.getJournalID() + "," +
                                j.getCategory() + "," +
                                j.getGenre() + "," +
                                j.getPageCount() + "," +
                                j.getIssueNumber() + "\n"
                );
            }

            System.out.println("Journals saved.");
        } catch (IOException e) {
            System.out.println("Error saving Journals");
            e.printStackTrace();
        }
    }



    //Saves detailed Media information.

    public static void saveMedia(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.write("ItemID,MediaID,Topic,FileSize,Format,Duration\n");

            for (Media m : Media.mediaList) {
                writer.write(
                        m.getItemID() + "," +
                                m.getMediaID() + "," +
                                m.getTopic() + "," +
                                m.getFileSize() + "," +
                                m.getFormat() + "," +
                                m.getDuration() + "\n"
                );
            }

            System.out.println("Media saved.");
        } catch (IOException e) {
            System.out.println("Error saving Media");
            e.printStackTrace();
        }
    }
}