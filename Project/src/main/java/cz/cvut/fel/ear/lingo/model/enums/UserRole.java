package cz.cvut.fel.ear.lingo.model.enums;

public enum UserRole {

    ADMIN("ROLE_ADMIN"), USER("ROLE_USER"), VIEWER("ROLE_VIEWER");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
