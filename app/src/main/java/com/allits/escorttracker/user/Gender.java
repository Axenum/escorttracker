package com.allits.escorttracker.user;

public enum Gender {

    MALE("MALE"), FEMALE("FEMALE"), UNKNOWN("UNKNOWN");

    private String value;

    Gender(String gender) {
        this.value = gender;
    }

    public static Gender fromValue(String value) {
        for (Gender cur : Gender.values()) {
            if (cur.value.equalsIgnoreCase(value)) {
                return cur;
            }
        }
        throw new IllegalArgumentException("No Gender for '" + value + "' found!");
    }

    public String getValue() {
        return value;
    }


}
