package pl.training.shop.payments.adapters.api;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.training.payments.PaymentDto;
import pl.training.payments.PaymentRequestDto;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentService;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Service
@Log
@RequiredArgsConstructor
public class RestTemplatePaymentService implements PaymentService {

    private static final int PREFERRED_INSTANCE = 0;

    private final PaymentApiMapper paymentApiMapper;
    private final DiscoveryClient discoveryClient;
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

    private URI getUri() {
        var instances = discoveryClient.getInstances(serviceName);
        if (instances.isEmpty()) {
            throw new IllegalStateException();
        }
        return instances.get(PREFERRED_INSTANCE).getUri().resolve(resource);
    }

}
