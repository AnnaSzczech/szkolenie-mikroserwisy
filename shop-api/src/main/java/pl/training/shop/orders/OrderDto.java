package pl.training.shop.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class OrderDto {

    public static final String CONFIRMED ="confirmed";
    public static final String NOT_CONFIRMED ="not_confirmed";

    @JsonProperty(access = READ_ONLY)
    @NonNull
    private Long id;
    @Min(1)
    @NonNull
    private Long ownerId;
    @NotEmpty
    private List<Long> productsIds;
    @JsonProperty(access = READ_ONLY)
    @NonNull
    private String status;

}
