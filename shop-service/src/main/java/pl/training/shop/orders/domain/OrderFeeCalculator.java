package pl.training.shop.orders.domain;

import lombok.Getter;
import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;
import pl.training.commons.money.LocalMoney;

@Log
public class OrderFeeCalculator {

    @Getter
    private FastMoney value;

    public void setValue(long value) {
        log.info("Updating order fee to: " + value);
        this.value = LocalMoney.of(value);
    }

}
