package pl.training.shop.orders.adapters.api;

import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import pl.training.shop.orders.OrderDto;
import pl.training.shop.orders.domain.Order;
import pl.training.shop.payments.domain.PaymentStatus;
import pl.training.shop.products.domain.ProductService;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
abstract class OrderApiMapper {

    @Autowired
    @Setter
    private ProductService productService;

    Order toDomain(OrderDto orderDto) {
        var products = orderDto.getProductsIds().stream()
                .map(productService::findById)
                .collect(Collectors.toList());
        return new Order(orderDto.getOwnerId(), products);
    }

    OrderDto toDto(Order order) {
        var status = order.getStatus() == PaymentStatus.PAID ? OrderDto.CONFIRMED : OrderDto.NOT_CONFIRMED;
        return new OrderDto(order.getId(), order.getOwnerId(), status);
    }

}
