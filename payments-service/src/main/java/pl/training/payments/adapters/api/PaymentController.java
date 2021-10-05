package pl.training.payments.adapters.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.training.payments.PaymentDto;
import pl.training.payments.PaymentRequestDto;
import pl.training.payments.PaymentsApi;
import pl.training.payments.domain.PaymentService;

import javax.validation.Valid;

import static pl.training.commons.web.UriBuilder.requestUriWithId;

@RestController
@RequiredArgsConstructor
class PaymentController implements PaymentsApi {

    private final PaymentApiMapper paymentApiMapper;
    private final PaymentService paymentService;

    @Override
    public ResponseEntity<PaymentDto> process(@Valid PaymentRequestDto paymentRequestDto) {
        var paymentRequest = paymentApiMapper.toDomain(paymentRequestDto);
        var payment = paymentService.process(paymentRequest);
        var paymentDto = paymentApiMapper.toDto(payment);
        var locationUri = requestUriWithId(paymentDto.getId());
        return ResponseEntity.created(locationUri)
                .body(paymentDto);
    }

    @Override
    public ResponseEntity<PaymentDto> findById(String id) {
        var payment = paymentService.findById(id);
        var paymentDto = paymentApiMapper.toDto(payment);
        return ResponseEntity.ok(paymentDto);
    }

}
