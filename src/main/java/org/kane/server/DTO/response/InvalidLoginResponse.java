package org.kane.server.DTO.response;

import lombok.Getter;

@Getter
public class InvalidLoginResponse{
    private final String message = "Invalid Login Request";
    private final String password = "Invalid Password";
}
