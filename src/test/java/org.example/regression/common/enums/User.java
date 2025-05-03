package org.example.regression.common.enums;


import lombok.Getter;
import lombok.Setter;

public class User {

    public static final String PASSWORD = "secret_sauce";

    //Roles
    public static Boolean administrator;
    public static Boolean readOnly;
    public static Boolean limited;
    public static Boolean restricted;


    @Getter
    @Setter
    private static String userName;
    @Getter
    @Setter
    private static String password;
    @Getter
    @Setter
    private static String email;
    @Getter
    @Setter
    private static String firstName;
    @Getter
    @Setter
    private static String lastName;

    public User() {
        userName = "standard_user";
        password = "secret_sauce";
        email = "test@test.com";
        firstName = "John";
        lastName = "Doe";
        administrator = true;
        readOnly = false;
        limited = false;
        restricted = false;
    }

    public static void setUserRole(UserRole role) {
        switch(role) {
            case ADMIN -> admin();
            case READ_ONLY -> readOnly();
            case RESTRICTED -> lockedOut();
            case LIMITED -> limited();
        }
    }

    public static void admin() {
        setUserName("standard_user");
        setUserName(PASSWORD);
        setEmail("standard_user@test.com");
        setFirstName("Standard");
        setLastName("User");
        administrator = true;
    }

    public static void readOnly() {
        setUserName("visual_user");
        setUserName(PASSWORD);
        setEmail("visual_user@test.com");
        setFirstName("Visual");
        setLastName("User");
        readOnly = true;
    }

    public static void lockedOut() {
        setUserName("locked_out_user");
        setUserName(PASSWORD);
        setEmail("locked_out_user@test.com");
        setFirstName("Locked Out");
        setLastName("User");
        restricted = true;
    }

    public static void limited() {
        setUserName("problem_user");
        setUserName(PASSWORD);
        setEmail("problem_user@test.com");
        setFirstName("Problem");
        setLastName("User");
        limited = true;
    }
}
