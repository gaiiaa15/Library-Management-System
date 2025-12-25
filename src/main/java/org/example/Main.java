package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {

                // Load all data
                CSVReaderUtil.loadAllData();

                // 1. Login and get the authenticated user
                User user = LoginManager.login();

                // 2. Route user to correct dashboard
                LoginManager.directUser(user);

        // Run librarian automated tests
        //Testing.runTests();

    }
}