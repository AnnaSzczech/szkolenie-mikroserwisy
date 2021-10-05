package pl.training.shop.payments.adapters.api;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pl.training.payments.PaymentRequestDto;
import pl.training.payments.PaymentsApi;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentService;

import java.util.Map;
import java.util.Optional;

@Primary
@Service
@Log
@RequiredArgsConstructor
public class FeignPaymentsService implements PaymentService {

    private final PaymentsApi paymentsApi;
    private final PaymentApiMapper paymentApiMapper;

    @Override
    public Optional<Payment> pay(FastMoney value, Map<String, String> additionalInfo) {
        var paymentRequestDto = new PaymentRequestDto(value.toString(), additionalInfo);
        try {
            var paymentDto = paymentsApi.process(paymentRequestDto).getBody();
            return Optional.of(paymentApiMapper.toDomain(paymentDto));
        } catch (FeignException exception) {
            log.warning("Payment failed: " + exception.getMessage());
        }
        return Optional.empty();
    }

}
