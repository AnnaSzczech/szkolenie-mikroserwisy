package pl.training.payments.adapters.logging;

import lombok.extern.java.Log;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.training.payments.domain.Payment;
import pl.training.payments.domain.PaymentRequest;

@Order(20)
@Component
@Aspect
@Log
public class ConsolePaymentLogger {

    @Pointcut("@annotation(pl.training.payments.domain.LogPayments)")
    void logPayments() {
    }

    @Before("logPayments() && args(paymentRequest)")
    public void beforePayment(PaymentRequest paymentRequest) {
        log.info("New payment request: " + paymentRequest);
    }

    @After("logPayments()")
    public void afterPayment() {
        log.info("Payment processed");
    }

    @AfterReturning(value = "logPayments()", returning = "payment")
    public void onSuccess(Payment payment) {
        log.info("Payment created: " + payment);
    }

    @AfterThrowing(value = "logPayments()", throwing = "exception")
    public void onFailure(Exception exception) {
        log.info("Payment failed: " + exception.getClass().getSimpleName());
    }

}
