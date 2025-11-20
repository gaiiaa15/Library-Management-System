package org.example;

import java.util.ArrayList;
import java.util.List;

//this class mainly sets and gets all the data needed for the admins and it inherits attributes that are not unique to the admin into
//admin inherits from the user class some of the attributes
public class Admin extends User {

    // static list shared by all admin objects
    public static List<Admin> admins = new ArrayList<>();

    // additional field that only admins have
    private String adminID;

    //empty constructor
    public Admin() {
        super();
    }

    //main constructor to set the admin fields
    public Admin(String userID, String firstName, String lastName, String role,
                 String email, String password, String adminID) {
        super(userID, firstName, lastName, role, email, password);
        //setter
        this.adminID = adminID;
    }

    //getter
    public String getAdminID() { return adminID; }

    //this is the display of the admin content
    @Override
    public String toString() {
        return  "\n============================" +
                "\n🛠️  ADMIN ACCOUNT" +
                "\n============================" +
                "\nUser ID     : " + getUserID() +
                "\nName        : " + getFirstName() + " " + getLastName() +
                "\nEmail       : " + getEmail() +
                "\nAdmin ID    : " + adminID +
                "\n============================\n";
    }
}