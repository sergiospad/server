package org.kane.server.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.kane.server.DTO.request.SignupRequest;
import org.kane.server.validations.annotations.PasswordMatches;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, SignupRequest> {

    @Override
    public boolean isValid(SignupRequest signupRequest, ConstraintValidatorContext constraintValidatorContext) {
        return signupRequest.getPassword().equals(signupRequest.getConfirmPassword());
    }

}