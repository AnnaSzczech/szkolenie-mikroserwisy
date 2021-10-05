package pl.training.payments;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.training.commons.money.Money;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {

    //@Money(minValue = 0)
    @NotNull
    @NonNull
    private String value;
    //@NotEmpty
    @NonNull
    private Map<String, String> properties;

}
