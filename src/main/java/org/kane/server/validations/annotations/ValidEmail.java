package org.kane.server.validations.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.kane.server.validations.EmailValidator;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
    String message() default "Invalid email";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
