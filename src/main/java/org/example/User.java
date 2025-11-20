package org.example;

import java.util.ArrayList;
import java.util.List;

public class User {
// a static list that holds all the user objects currently loaded in memory
    //its static so it belongs to the user class itself
    //it is used in log in manager.
    public static List<User> users = new ArrayList<>();
//the core attributes of the user
//they are private hence only accessible inside this class (encapsulation)
    private String userID;
    private String firstName;
    private String lastName;
    private String userRole;
    private String email;
    private String password;

//for each user it holds the item id of the resources that the user has borrowed
    private  List<String> borrowedResources = new ArrayList<>();

//empty constructor
    public User() {}
// a constructor that only sets the user id, useful if i need a reference by id only
    public User(String userID) {
        this.userID = userID;
    }

// this is the main constructor used loading from Users.csv
    public User(String userID, String firstName, String lastName,
                String userRole, String email, String password) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.email = email;
        this.password = password;
    }

    // GETTERS
    public String getUserID() { return userID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUserRole() { return userRole; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public List<String> getBorrowedResources() { return borrowedResources; }

    //Setters
    public void setFirstName(String fn) { this.firstName = fn; }
    public void setLastName(String ln) { this.lastName = ln; }
    public void setUserRole(String ur) { this.userRole = ur; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String pw) { this.password = pw; }



    //Borrowing a resource method
    // it returns true if the item has been borrowed.
    public boolean borrowResource(LibraryResources resource) {
        //checks of the resource is available if not outputs an error
        if (!resource.getAvailability().equalsIgnoreCase("Yes")) {
            System.out.println("Resource is not available.");
            return false;
        }

// second check , the quantity must be greater than 0
        //if the amount is 0 it means none are available.
        if (resource.getAmountAvailable() <= 0) {
            System.out.println("No copies left to borrow.");
            return false;
        }
        // this prevents the same user form borrowing the same item twice
        if (borrowedResources.contains(resource.getItemID())) {
            System.out.println("You already borrowed this resource.");
            return false;
        }
        // adds the item to the personal borrowed list in memory
        borrowedResources.add(resource.getItemID()); // store record

        // update resource availability
        resource.setAmountAvailable(resource.getAmountAvailable() - 1);

        //if the item was the last one in stock it will make it unavailable
        if (resource.getAmountAvailable() == 0) {
            resource.setAvailability("No");
        }

        System.out.println("Borrowed successfully: " + resource.getName());
        return true;
    }


    //return method
    public boolean returnResource(LibraryResources resource) {
        //checks that the user has borrowed this item
        //if the item is not in the borrowed list then they cant return it
        if (!borrowedResources.contains(resource.getItemID())) {
            System.out.println("You did not borrow this resource.");
            return false;
        }
        // remove the item from the borrowed list
        borrowedResources.remove(resource.getItemID());

        // update resource amount
        resource.setAmountAvailable(resource.getAmountAvailable() + 1);
        resource.setAvailability("Yes");

        //confirms the return
        System.out.println("Returned: " + resource.getName());
        return true;
    }



    public static String getUserTableHeader() {
        return String.format(
                "%-8s | %-12s | %-12s | %-10s | %-25s | %-20s",
                "UserID",
                "FirstName",
                "LastName",
                "Role",
                "Email",
                "Borrowed"
        ) +
                "\n-----------------------------------------------------------------------------------------------";
    }

    // ================================
    // TABLE ROW (toString)
    // ================================
    @Override
    public String toString() {

        String borrowed = borrowedResources.isEmpty()
                ? "-"
                : String.join(" | ", borrowedResources);

        return String.format(
                "%-8s | %-12s | %-12s | %-10s | %-25s | %-20s",
                userID,
                firstName,
                lastName,
                userRole,
                email,
                borrowed
        );
    }
}