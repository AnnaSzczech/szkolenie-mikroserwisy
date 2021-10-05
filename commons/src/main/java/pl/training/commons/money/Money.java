package pl.training.commons.money;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MoneyValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Money {

    String message() default "InvalidPayment";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long minValue() default 10;

}
