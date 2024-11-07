package com.aamlid.amesbury.model;

import lombok.Data;

@Data
public class UserResponse {
    private String username;
    private String email;

    public UserResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }
}