package entities;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");


    private final String name;
    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Role fromString(String text) {
        for (Role role : Role.values()) {
            if (role.name.equalsIgnoreCase(text)) {
                return role;
            }
        }
        return null;
    }
}
