package com.derick.server.domain.enums;

public enum ClientRole {
    ADMIN(0, "ADMIN"),
    USER_TRIATOR(1, "TRIATOR"),
    USER_FINISHER(2, "FINISHER");

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
