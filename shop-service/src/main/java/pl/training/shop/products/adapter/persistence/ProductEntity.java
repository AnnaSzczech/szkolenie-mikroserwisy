package pl.training.shop.products.adapter.persistence;


import lombok.Getter;
import lombok.Setter;
import org.javamoney.moneta.FastMoney;
import pl.training.commons.money.FastMoneyConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity(name = "Product")
@Setter
@Getter
public class ProductEntity {

    @GeneratedValue
    @Id
    private Long id;
    private String name;
    @Convert(converter = FastMoneyConverter.class)
    private FastMoney price;

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof ProductEntity)) {
            return false;
        }
        var productEntity = (ProductEntity) otherObject;
        return Objects.equals(id, productEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
