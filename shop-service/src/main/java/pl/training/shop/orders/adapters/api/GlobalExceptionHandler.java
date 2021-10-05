package pl.training.shop.orders.adapters.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.training.payments.ExceptionDto;
import pl.training.shop.orders.domain.OrderNotFoundException;
import pl.training.shop.products.domain.ProductNotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Locale;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice(basePackages = "pl.training.shop.orders.adapters.api")
@RequiredArgsConstructor
class GlobalExceptionHandler {

    private static final String SEPARATOR = " - ";

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    ResponseEntity<ExceptionDto> onException(Exception exception, Locale locale) {
        return createResponse(exception, INTERNAL_SERVER_ERROR, locale);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    ResponseEntity<ExceptionDto> onProductNotFound(ProductNotFoundException exception, Locale locale) {
        return createResponse(exception, NOT_FOUND, locale);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    ResponseEntity<ExceptionDto> onOrderNotFound(OrderNotFoundException exception, Locale locale) {
        return createResponse(exception, NOT_FOUND, locale);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ExceptionDto> onValidationException(MethodArgumentNotValidException exception, Locale locale) {
        var errors = getErrors(exception);
        var description = prepareDescription(mapExceptionToKey(exception), new Object[] { errors }, locale);
        return ResponseEntity.badRequest().body(new ExceptionDto(description, LocalDateTime.now()));
    }

    private String getErrors(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + SEPARATOR + error.getDefaultMessage())
                .collect(joining());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ExceptionDto> onValidationException(ConstraintViolationException exception, Locale locale) {
        var errors = getErrors(exception, locale);
        var description = prepareDescription(mapExceptionToKey(exception), new Object[] { errors }, locale);
        return ResponseEntity.badRequest().body(new ExceptionDto(description, LocalDateTime.now()));
    }

    private String getErrors(ConstraintViolationException exception, Locale locale) {
        return exception.getConstraintViolations().stream()
                .map(error -> error.getPropertyPath() + SEPARATOR + prepareDescription(error.getMessage(), new Object[]{}, locale))
                .collect(joining());
    }

    private ResponseEntity<ExceptionDto> createResponse(Exception exception, HttpStatus httpStatus, Locale locale) {
        var description = prepareDescription(mapExceptionToKey(exception), new String[] {}, locale);
        exception.printStackTrace();
        return ResponseEntity.status(httpStatus).body(new ExceptionDto(description, LocalDateTime.now()));
    }

    private String mapExceptionToKey(Exception exception) {
        return exception.getClass().getSimpleName();
    }

    private String prepareDescription(String key, Object[] parameters, Locale locale) {
        String description;
        try {
            description = messageSource.getMessage(key, parameters, locale);
        } catch (NoSuchMessageException ex) {
            description = key;
        }
        return description;
    }

}
