package pl.training.shop.products.adapter.persistence;

import org.mapstruct.Mapper;
import pl.training.shop.products.domain.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductPersistenceMapper {

    ProductEntity toEntity(Product product);

    Product toDomain(ProductEntity productEntity);

    List<ProductEntity> toEntities(List<Product> products);

    List<Product> toDomain(List<ProductEntity> productEntities);

}
