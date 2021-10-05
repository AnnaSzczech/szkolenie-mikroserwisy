package pl.training.payments.domain;

import pl.training.commons.time.TimeProvider;

public class PaymentServiceFactory {

    public PaymentService create(TimeProvider timeProvider, PaymentRepository paymentRepository, PaymentStatusChangeNotifier paymentStatusChangeNotifier) {
        return new PaymentService(new UUIDPaymentIdGenerator(), timeProvider, paymentRepository, paymentStatusChangeNotifier);
    }

}
