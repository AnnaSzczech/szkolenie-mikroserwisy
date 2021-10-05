package pl.training.shop.payments.adapters.api;

import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import pl.training.payments.PaymentDto;
import pl.training.payments.PaymentStatusDto;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentStatus;

@Mapper(componentModel = "spring")
public interface PaymentApiMapper {

    Payment toDomain(PaymentDto paymentDto);

    @ValueMapping(source = "NOT_CONFIRMED", target = "NOT_PAID")
    @ValueMapping(source = "CONFIRMED", target = "PAID")
    PaymentStatus toDomain(PaymentStatusDto paymentStatusDto);

}
