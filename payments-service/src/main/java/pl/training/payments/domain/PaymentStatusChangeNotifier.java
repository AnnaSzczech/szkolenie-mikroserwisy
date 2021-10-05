package pl.training.payments.domain;

public interface PaymentStatusChangeNotifier {

    void sendUpdate(Payment payment);

}
