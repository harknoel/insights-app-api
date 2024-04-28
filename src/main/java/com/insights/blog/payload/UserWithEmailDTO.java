package com.insights.blog.payload;

import lombok.Getter;

@Getter
public class UserWithEmailDTO extends UserDTO {

    private final String email;

    public UserWithEmailDTO(Integer userId, String firstname, String lastname, String email) {
        super(userId, firstname, lastname);
        this.email = email;
    }
}
