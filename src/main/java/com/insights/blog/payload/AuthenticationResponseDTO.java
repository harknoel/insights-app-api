package com.insights.blog.payload;

import lombok.*;

@Getter
@Setter
public class AuthenticationResponseDTO extends UserDTO {
    String token;

    public AuthenticationResponseDTO(Integer userId, String firstname, String lastname, String token) {
        super(userId, firstname, lastname);
        this.token = token;
    }
}
