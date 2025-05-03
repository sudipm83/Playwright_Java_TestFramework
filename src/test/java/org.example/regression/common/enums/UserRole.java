package org.example.regression.common.enums;

public enum UserRole {
    ADMIN("Admin"),
    LIMITED("Limited"),
    READ_ONLY("Read Only"),
    RESTRICTED("Restricted");

    private final String role;

    UserRole(String role) { this.role = role; }

    public String getRole() { return role; }
}
