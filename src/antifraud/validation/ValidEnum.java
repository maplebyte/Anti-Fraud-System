package antifraud.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Constraint(validatedBy = EnumValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull(message = "Value mustn't be null")
public @interface ValidEnum {

    Class<? extends Enum<?>> clazz();

    String message() default "Value is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}