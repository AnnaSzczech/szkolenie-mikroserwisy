package pl.training.payments.adapters.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import pl.training.payments.PaymentDto;
import pl.training.payments.PaymentRequestDto;
import pl.training.payments.PaymentStatusDto;
import pl.training.commons.money.FastMoneyMapper;
import pl.training.payments.domain.Payment;
import pl.training.payments.domain.PaymentRequest;
import pl.training.payments.domain.PaymentStatus;

@Mapper(componentModel = "spring", uses = FastMoneyMapper.class)
interface PaymentApiMapper {

    @Mapping(source = "properties", target = "additionalInfo")
    PaymentRequest toDomain(PaymentRequestDto paymentRequestDto);

    PaymentDto toDto(Payment payment);

    @ValueMapping(source = "STARTED", target = "NOT_CONFIRMED")
    @ValueMapping(source = "FAILED", target = "NOT_CONFIRMED")
    @ValueMapping(source = "CANCELED", target = "NOT_CONFIRMED")
    PaymentStatusDto toDto(PaymentStatus status);

}
