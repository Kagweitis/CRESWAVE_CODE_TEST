package com.kagwe.creswave_code_test.Entities;

import java.util.Optional;

public enum Role {

    ADMIN,
    USER;

    public static Optional<Role> fromString(String value) {
        try {
            return Optional.of(Role.valueOf(value.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
