package com.tinotendachingwena.website.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class ServiceValidator implements ConstraintValidator<ServiceValidatorInterface, String> {
    private final List<String> services = Arrays.asList("Android Application", "Team Project", "Website");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if  (value == null)
            return false;

        return services.contains(value);
    }
}