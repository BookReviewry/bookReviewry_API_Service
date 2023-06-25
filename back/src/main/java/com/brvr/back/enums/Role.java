package com.brvr.back.enums;

import lombok.Getter;

@Getter
public enum Role {

    ROLE_GUEST("Guest"), ROLE_USER("User");

    private String description;

    Role(String description) {
        this.description = description;
    }
}
