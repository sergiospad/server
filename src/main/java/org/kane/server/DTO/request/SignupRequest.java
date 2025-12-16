package org.kane.server.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.kane.server.validations.annotations.PasswordMatches;
import org.kane.server.validations.annotations.ValidEmail;

@Data
@PasswordMatches
public class SignupRequest {

    @Email(message="It should have email format")
    @NotBlank(message="User email is required")
    @ValidEmail
    private String email;

    @NotEmpty(message = "Please enter your password")
    private String password;

    @NotEmpty(message = "Please confirm your password")
    @Size(min=6)
    private String confirmPassword;

    @NotEmpty(message = "Please enter your firstname")
    private String firstname;

    @NotEmpty(message = "Please enter your lastname")
    private String lastname;

    @NotEmpty(message = "Please enter your login")
    private String username;

}
