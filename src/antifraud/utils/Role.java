package antifraud.utils;

public enum Role {
    ADMINISTRATOR, MERCHANT, SUPPORT;

    public String getRoleWithPrefix() {
        return "ROLE_" + name();
    }

}
