package pl.training.shop.orders;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RequestMapping("orders")
public interface OrdersApi {

    @PostMapping
    ResponseEntity<OrderDto> add(@Valid @RequestBody OrderDto orderDto);

    @GetMapping("{id}")
    ResponseEntity<OrderDto> findById(@PathVariable Long id);

}
