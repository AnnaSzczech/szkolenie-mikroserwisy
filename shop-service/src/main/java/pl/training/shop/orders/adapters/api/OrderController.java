package pl.training.shop.orders.adapters.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.training.shop.orders.OrderDto;
import pl.training.shop.orders.OrdersApi;
import pl.training.shop.orders.domain.OrderService;

import javax.validation.Valid;

import static pl.training.commons.web.UriBuilder.requestUriWithId;

@RestController
@RequiredArgsConstructor
class OrderController implements OrdersApi {

    private final OrderApiMapper orderApiMapper;
    private final OrderService orderService;

    @Override
    public ResponseEntity<OrderDto> add(@Valid OrderDto orderDto) {
        var order = orderApiMapper.toDomain(orderDto);
        var addedOrder = orderService.add(order);
        var addedOrderDto = orderApiMapper.toDto(addedOrder);
        var locationUri = requestUriWithId(addedOrderDto.getId());
        return ResponseEntity.created(locationUri)
                .body(addedOrderDto);
    }

    @Override
    public ResponseEntity<OrderDto> findById(Long id) {
        var order = orderService.findById(id);
        var orderDto = orderApiMapper.toDto(order);
        return ResponseEntity.ok(orderDto);
    }

}
