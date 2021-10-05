package pl.training.payments;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient("payments-service")
@Validated
@RequestMapping(value = "payments")
public interface PaymentsApi {

    @PostMapping
    ResponseEntity<PaymentDto> process(@Valid @RequestBody PaymentRequestDto paymentRequestDto);

    @GetMapping("{id}")
    ResponseEntity<PaymentDto> findById(@PathVariable("id") String id);

}
