package pl.training.shop.payments.domain;

import org.javamoney.moneta.FastMoney;

import java.util.Map;
import java.util.Optional;

public interface PaymentService {

    Optional<Payment> pay(FastMoney value, Map<String, String> additionalInfo);

}
