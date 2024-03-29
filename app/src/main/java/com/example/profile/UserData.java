package com.example.profile;

import java.io.Serializable;

public class UserData implements Serializable {

    private String firstName;
    private String lastName;
    private String email;

    public UserData(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getter methods

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
