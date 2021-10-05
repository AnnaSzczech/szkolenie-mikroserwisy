package pl.training.shop.payments.adapters.api;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.training.payments.PaymentDto;
import pl.training.payments.PaymentRequestDto;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentService;

import java.util.Map;
import java.util.Optional;

@Service
@Log
@RequiredArgsConstructor
public class BalancedRestTemplatePaymentService implements PaymentService {

    private final PaymentApiMapper paymentApiMapper;
    private final RestTemplate restTemplate;

    @Value("${payments-service.name}")
    @Setter
    private String serviceName;
    @Value("${payments-service.resource}")
    private String resource;

    @Override
    public Optional<Payment> pay(FastMoney value, Map<String, String> additionalInfo) {
        var paymentRequestDto = new PaymentRequestDto(value.toString(), additionalInfo);
        try {
            var paymentDto = restTemplate.postForObject(getUri(), paymentRequestDto, PaymentDto.class);
            if (paymentDto == null) {
                return Optional.empty();
            }
            return Optional.of(paymentApiMapper.toDomain(paymentDto));
        } catch (HttpClientErrorException exception) {
            log.warning("Payment failed: " + exception.getMessage());
        }
        return Optional.empty();
    }

    private String getUri() {
        return "http://" + serviceName + resource;
    }

}
