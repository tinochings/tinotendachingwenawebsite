package com.tinotendachingwena.website.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ServiceValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceValidatorInterface {
    String message() default "Service has been tampered with";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
