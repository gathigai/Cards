package com.gathigai.cards.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ColorValidator implements ConstraintValidator<Color, String> {
    @Override
    public void initialize(Color constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null){
            Pattern pattern = Pattern.compile("#([a-fA-F0-9]{6})");
            return pattern.matcher(value).matches();
        }
        return true;
    }
}
