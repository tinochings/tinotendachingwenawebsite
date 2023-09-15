package com.tinotendachingwena.website.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class BudgetValidator implements ConstraintValidator<BudgetValidatorInterface, String> {
    private final  List<String> budgetList = Arrays.asList("$0 - $150","$150 - $300", "$300 - $550", "$500+");
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if  (value == null)
            return false;

        return budgetList.contains(value);
    }
}