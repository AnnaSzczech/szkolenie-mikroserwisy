package pl.training.shop.orders.adapters.persistence;

import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import pl.training.shop.orders.domain.Order;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.products.adapter.persistence.ProductPersistenceMapper;

@Mapper(componentModel = "spring")
abstract class OrderPersistenceMapper {

    @Autowired
    @Setter
    private ProductPersistenceMapper productPersistenceMapper;

    OrderEntity toEntity(Order order) {
        var entity = new OrderEntity();
        entity.setOwnerId(order.getOwnerId());
        entity.setProducts(productPersistenceMapper.toEntities(order.getProducts()));
        entity.setStatus(order.getPayment().getStatus());
        entity.setPaymentId(order.getPayment().getId());
        return entity;
    }

    Order toDomain(OrderEntity orderEntity) {
        var payment = new Payment(orderEntity.getPaymentId(), orderEntity.getStatus());
        var order = new Order(orderEntity.getOwnerId(), productPersistenceMapper.toDomain(orderEntity.getProducts()));
        order.setId(orderEntity.getId());
        order.setPayment(payment);
        return order;
    }

}
