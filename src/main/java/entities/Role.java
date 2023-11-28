package entities;

public enum Role {
    USER("User"),
    ADMIN("Admin");


    private String name;
    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
