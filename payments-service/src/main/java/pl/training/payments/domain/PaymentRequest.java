package pl.training.payments.domain;

import lombok.*;
import org.javamoney.moneta.FastMoney;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @NonNull
    private FastMoney value;
    @NonNull
    private Map<String, String> additionalInfo;

}
