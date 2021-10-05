package pl.training.shop.products.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.FastMoney;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;
    @NonNull
    private String name;
    @NonNull
    private FastMoney price;

}
