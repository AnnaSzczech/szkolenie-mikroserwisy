package pl.training.payments.adapters.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.training.payments.domain.Payment;
import pl.training.payments.domain.PaymentStatusChangeNotifier;

@Service
@RequiredArgsConstructor
public class SpringPaymentStatusChangeNotifier implements PaymentStatusChangeNotifier {

    private final Source source;
    private final PaymentMessagingMapper paymentMessagingMapper;

    @Override
    public void sendUpdate(Payment payment) {
        var paymentDto = paymentMessagingMapper.toDto(payment);
        var message = MessageBuilder.withPayload(paymentDto).build();
        source.output().send(message);
    }

}
