package qal.fast.configs.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {
    private Enum<?>[] enumValues;

    @Override
    public void initialize(ValueOfEnum annotation) {
        enumValues = annotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        for (Enum<?> enumValue : enumValues) {
            if (enumValue.name().equals(value.toString())) {
                return true;
            }
        }

        return false;
    }
}
