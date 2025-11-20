package org.example;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    //static list that holds all the student objects
    public static List<Student> students = new ArrayList<>();

    //unique field to the student that the user does not have
    private String studentID;


    //empty constructor
    public Student() {
        super();
    }


    //sets the fields from the student.csv file
    public Student(String userID, String firstName, String lastName,
                   String role, String email, String password, String studentID) {
        //calls the user constructor to set the fields from it
        super(userID, firstName, lastName, role, email, password);
        this.studentID = studentID;
    }

    //getter
    public String getStudentID() { return studentID; }
    //setter
    public void setStudentID(String studentID) { this.studentID = studentID; }

    @Override
    public String toString() {
        return "Student{" +
                "UserID='" + getUserID() + '\'' +
                ", FirstName='" + getFirstName() + '\'' +
                ", LastName='" + getLastName() + '\'' +
                ", Email='" + getEmail() + '\'' +
                ", StudentID='" + studentID + '\'' +
                '}';
    }
}