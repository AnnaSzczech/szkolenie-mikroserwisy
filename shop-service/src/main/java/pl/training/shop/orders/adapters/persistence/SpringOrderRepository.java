package pl.training.shop.orders.adapters.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.training.shop.orders.domain.Order;
import pl.training.shop.orders.domain.OrderRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class SpringOrderRepository implements OrderRepository {

    private final OrderPersistenceMapper orderPersistenceMapper;
    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        var entity = orderPersistenceMapper.toEntity(order);
        var savedEntity = orderJpaRepository.saveAndFlush(entity);
        return orderPersistenceMapper.toDomain(entity);
    }

    @Override
    public void update(Order order) {
        save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderJpaRepository.findById(id)
                .map(orderPersistenceMapper::toDomain);
    }

}
