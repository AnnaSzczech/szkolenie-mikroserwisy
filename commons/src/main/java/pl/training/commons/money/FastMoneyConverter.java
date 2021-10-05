package pl.training.commons.money;

import org.javamoney.moneta.FastMoney;

import javax.persistence.AttributeConverter;

public class FastMoneyConverter implements AttributeConverter<FastMoney, String> {

    @Override
    public String convertToDatabaseColumn(FastMoney fastMoney) {
        return fastMoney != null ? fastMoney.toString() : null;
    }

    @Override
    public FastMoney convertToEntityAttribute(String text) {
        return text != null ? FastMoney.parse(text) : LocalMoney.zero();
    }

}
