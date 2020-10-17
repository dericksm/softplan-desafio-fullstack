package com.derick.server.domain.enums;

public enum ClientRole {
    ADMIN(1, "ROLE_ADMIN"),
    USER_TRIATOR(2, "USER_TRIATOR"),
    USER_FINISHER(3, "USER_FINISHER");

    private int value;
    private String description;

    ClientRole(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static ClientRole toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (ClientRole value : ClientRole.values()) {
            if (cod.equals(value.getValue())) {
                return value;
            }
        }

        throw new IllegalArgumentException("Invalid role");
    }
}
