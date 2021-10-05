package pl.training.shop.orders.adapters.persistence;


import lombok.Getter;
import lombok.Setter;
import pl.training.shop.payments.domain.PaymentStatus;
import pl.training.shop.products.adapter.persistence.ProductEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table(name = "orders")
@Entity(name = "Order")
@Setter
@Getter
class OrderEntity {

    @GeneratedValue
    @Id
    private Long id;
    private Long ownerId;
    @ManyToMany
    private List<ProductEntity> products;
    private PaymentStatus status;
    private String paymentId;

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof ProductEntity)) {
            return false;
        }
        var productEntity = (ProductEntity) otherObject;
        return Objects.equals(id, productEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
