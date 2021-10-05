package pl.training.shop.orders.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.FastMoney;
import pl.training.commons.money.LocalMoney;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentStatus;
import pl.training.shop.products.domain.Product;

import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;
    @NonNull
    private Long ownerId;
    @NonNull
    private List<Product> products;
    private Payment payment;

    public PaymentStatus getStatus() {
        return payment.getStatus();
    }

    public FastMoney getTotalValue() {
        return products.stream()
                .map(Product::getPrice)
                .reduce(LocalMoney.zero(), FastMoney::add);
    }

}
