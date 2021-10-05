package pl.training.shop.payments.adapters.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;
import pl.training.payments.PaymentDto;

@Service
@Log
@RequiredArgsConstructor
class PaymentStatusChangeObserver {

    private final PaymentMessagingMapper paymentMessagingMapper;

    @StreamListener(Sink.INPUT)
    void update(PaymentDto paymentDto) {
        var payment = paymentMessagingMapper.toDomain(paymentDto);
        log.info("Payment status update: " + payment);
    }

}
