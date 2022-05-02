package antifraud.utils;

public enum Role {
    USER, ADMIN;

    public String getRoleWithPrefix() {
        return "ROLE_" + name();
    }

}
