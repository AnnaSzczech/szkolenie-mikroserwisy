package pl.training.payments.domain;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import pl.training.commons.time.TimeProvider;

import static lombok.AccessLevel.PACKAGE;

@Log
@RequiredArgsConstructor(access = PACKAGE)
public class PaymentService {

    private final PaymentIdGenerator paymentIdGenerator;
    private final TimeProvider timeProvider;
    private final PaymentRepository paymentRepository;
    private final PaymentStatusChangeNotifier paymentStatusChangeNotifier;

    @LogPayments
    public Payment process(PaymentRequest paymentRequest) {
        validate(paymentRequest);
        var payment = Payment.builder()
                .id(paymentIdGenerator.getNext())
                .value(paymentRequest.getValue())
                .timestamp(timeProvider.getTimestamp())
                .status(PaymentStatus.STARTED)
                .build();
        log.info("Processing payment: " + payment);
        fakePaymentProcess(payment);
        return paymentRepository.save(payment);
    }

    private void validate(PaymentRequest paymentRequest) {
        if (paymentRequest.getValue().isNegativeOrZero()) {
            throw new InvalidPaymentRequest();
        }
    }

    public Payment findById(String id) {
        return paymentRepository.findById(id)
                .orElseThrow(PaymentNotFoundException::new);
    }

    private void fakePaymentProcess(Payment payment) {
        new Thread(() -> {
            fakeDelay();
            payment.setStatus(PaymentStatus.CONFIRMED);
            paymentRepository.save(payment);
            paymentStatusChangeNotifier.sendUpdate(payment);
        }).start();
    }

    @SneakyThrows
    private void fakeDelay() {
        Thread.sleep(10_000);
    }

}
