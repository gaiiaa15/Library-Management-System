package org.example;

import java.util.Scanner;

//this class specifically focuses on one of the admin features
public class AdminManager {

    private static Scanner scanner = new Scanner(System.in);

    public static void createNewUser() {

        System.out.println("\n===== CREATE NEW USER =====");

        // 1. Collect details
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Role (admin / librarian / student): ");
        String role = scanner.nextLine().trim().toLowerCase();

        // Validate role
        if (!role.equals("admin") && !role.equals("librarian") && !role.equals("student")) {
            System.out.println(" Invalid role. Must be admin, librarian, or student.");
            return;
        }

        // 2. Generate UserID (U001+)
        String userID = "U" + String.format("%03d", User.users.size() + 1);

        // 3. Create base User object
        User newUser = new User(userID, firstName, lastName, role, email, password);
        User.users.add(newUser);

        // 4. Create role-specific object
        switch (role) {
            case "admin":
                String adminID = "A" + String.format("%03d", Admin.admins.size() + 1);
                Admin newAdmin = new Admin(userID, firstName, lastName, role, email, password, adminID);
                Admin.admins.add(newAdmin);
                break;

            case "librarian":
                String staffID = "L" + String.format("%03d", Librarian.librarians.size() + 1);
                Librarian newLib = new Librarian(userID, firstName, lastName, role, email, password, staffID);
                Librarian.librarians.add(newLib);
                break;

            case "student":
                String studentID = "S" + String.format("%03d", Student.students.size() + 1);
                Student newStudent = new Student(userID, firstName, lastName, role, email, password, studentID);
                Student.students.add(newStudent);
                break;
        }

        // 5. Save everything
        CSVWriterUtil.saveUsers("src/main/resources/Users.csv");
        CSVWriterUtil.saveAdmins("src/main/resources/Admins.csv");
        CSVWriterUtil.saveLibrarians("src/main/resources/Librarians.csv");
        CSVWriterUtil.saveStudents("src/main/resources/Students.csv");

        System.out.println("\nUser created successfully!");
        System.out.println("Assigned UserID: " + userID);
    }
}