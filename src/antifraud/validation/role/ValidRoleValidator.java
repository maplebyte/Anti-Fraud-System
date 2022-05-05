package antifraud.validation.role;

import antifraud.utils.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ValidRoleValidator implements ConstraintValidator<ValidRole, String> {

    private List<String> roleList = null;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return roleList.contains(value);
    }

    @Override
    public void initialize(ValidRole constraintAnnotation) {

        roleList = new ArrayList<>();

        Class<? extends Enum<?>> enumClass = Role.class;

        for (@SuppressWarnings("rawtypes") Enum enumVal : enumClass.getEnumConstants()) {
            roleList.add(enumVal.name());
        }

    }

}
