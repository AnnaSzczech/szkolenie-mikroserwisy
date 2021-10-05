package pl.training.commons.money;

import org.javamoney.moneta.FastMoney;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Locale;

public class LocalMoney {

    public static FastMoney of(Number number) {
        var currency = getCurrencyUnit();
        return FastMoney.of(number, currency);
    }

    public static FastMoney zero() {
        var currency = getCurrencyUnit();
        return FastMoney.zero(currency);
    }

    private  static CurrencyUnit getCurrencyUnit() {
        var locale = Locale.getDefault();
        return Monetary.getCurrency(locale);
    }

}
