package pl.training.shop.commons.resilience;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

@Service
@Aspect
@Log
public class Repeater {

    @Around("@annotation(Retry)")
    public Object tryExecute(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        var method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        var annotation = AnnotationUtils.findAnnotation(method, Retry.class);
        if (annotation == null) {
            throw new IllegalStateException();
        }
        var attempts = annotation.attempts();
        var currentAttempt = 0;
        Throwable throwable;
        do {
            currentAttempt++;
            log.info(String.format("%s method execution attempt %d", method.getName(), currentAttempt));
            try {
                return proceedingJoinPoint.proceed();
            } catch (Throwable currentThrowable) {
                throwable = currentThrowable;
            }
        } while (currentAttempt < attempts);
        throw throwable;
    }

}