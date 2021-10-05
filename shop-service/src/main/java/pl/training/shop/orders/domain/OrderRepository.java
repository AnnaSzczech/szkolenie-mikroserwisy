package pl.training.shop.orders.domain;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    void update(Order order);

    Optional<Order> findById(Long id);

}
