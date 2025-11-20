package org.example;

import java.util.ArrayList;
import java.util.List;

public class Librarian extends User {

    //static list of librarians
    public static List<Librarian> librarians = new ArrayList<>();

    //field only existent for librarians
    private String staffID;

    //empty constructor
    public Librarian() {
        super();
    }

    //main constructor
    public Librarian(String userID, String firstName, String lastName,
                     String role, String email, String password, String staffID) {
        super(userID, firstName, lastName, role, email, password);
        this.staffID = staffID;
    }

    //getter
    public String getStaffID() { return staffID; }
    //setter
    public void setStaffID(String staffID) { this.staffID = staffID; }

    @Override
    public String toString() {
        return  "\n============================" +
                "\n📚 LIBRARIAN ACCOUNT" +
                "\n============================" +
                "\nUser ID     : " + getUserID() +
                "\nName        : " + getFirstName() + " " + getLastName() +
                "\nEmail       : " + getEmail() +
                "\nStaff ID    : " + staffID +
                "\n============================\n";
    }
}
