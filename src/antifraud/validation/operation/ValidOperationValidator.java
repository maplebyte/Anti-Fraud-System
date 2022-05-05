package antifraud.validation.operation;

import antifraud.utils.Operation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ValidOperationValidator implements ConstraintValidator<ValidOperation, String> {

    private List<String> operationList = null;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return operationList.contains(value);
    }

    @Override
    public void initialize(ValidOperation constraintAnnotation) {

        operationList = new ArrayList<>();

        Class<? extends Enum<?>> enumClass = Operation.class;

        for (@SuppressWarnings("rawtypes") Enum enumVal : enumClass.getEnumConstants()) {
            operationList.add(enumVal.name());
        }

    }
}
