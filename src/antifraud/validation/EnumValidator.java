package antifraud.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private List<String> values = null;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return values.contains(value);
    }

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        values = new ArrayList<>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.clazz();

        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = enumClass.getEnumConstants();

        for (@SuppressWarnings("rawtypes") Enum enumVal : enumValArr) {
            values.add(enumVal.toString());
        }
    }

}