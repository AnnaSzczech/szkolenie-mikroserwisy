package pl.training.shop.orders.domain;

import lombok.RequiredArgsConstructor;
import pl.training.shop.commons.resilience.Retry;
import pl.training.shop.payments.domain.PaymentFailedException;
import pl.training.shop.payments.domain.PaymentService;

import static java.util.Collections.emptyMap;

@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final OrderFeeCalculator orderFeeCalculator;

    @Retry(attempts = 4)
    public Order add(Order order) {
        var paymentValue = order.getTotalValue().add(orderFeeCalculator.getValue());
        var payment = paymentService.pay(paymentValue, emptyMap())
                .orElseThrow(PaymentFailedException::new);
        order.setPayment(payment);
        return orderRepository.save(order);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
    }

}
