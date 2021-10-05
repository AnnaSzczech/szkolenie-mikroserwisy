package pl.training.commons.money;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.lang.Double.parseDouble;

public class MoneyValidator implements ConstraintValidator<Money, String> {

    private static final String MONEY_EXPRESSION = "\\d+(.\\d+)? [A-Z]+";
    private static final String CURRENCY_SEPARATOR = " ";

    private long minValue;

    @Override
    public void initialize(Money constraintAnnotation) {
        minValue = constraintAnnotation.minValue();
    }

    @Override
    public boolean isValid(String text, ConstraintValidatorContext constraintValidatorContext) {
        if (text.matches(MONEY_EXPRESSION)) {
            var value = text.split(CURRENCY_SEPARATOR);
            try {
                return parseDouble(value[0]) >= minValue;
            } catch (NumberFormatException exception) {
                return false;
            }
        }
        return false;
    }

}
