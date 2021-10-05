package pl.training.payments.adapters.persistence;

import lombok.Getter;
import lombok.Setter;
import org.javamoney.moneta.FastMoney;
import pl.training.commons.money.FastMoneyConverter;
import pl.training.payments.domain.PaymentStatus;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.Objects;

@Entity(name = "Payment")
@Setter
@Getter
class PaymentEntity {

    @Id
    private String id;
    @Convert(converter = FastMoneyConverter.class)
    private FastMoney value;
    private Instant timestamp;
    private PaymentStatus status;

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof PaymentEntity)) {
            return false;
        }
        var otherEntity = (PaymentEntity) otherObject;
        return Objects.equals(id, otherEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
