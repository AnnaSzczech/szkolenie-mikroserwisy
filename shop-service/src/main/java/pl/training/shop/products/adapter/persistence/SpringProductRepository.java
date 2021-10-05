package pl.training.shop.products.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.training.shop.products.domain.Product;
import pl.training.shop.products.domain.ProductRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class SpringProductRepository implements ProductRepository {

    private final ProductPersistenceMapper productPersistenceMapper;
    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product save(Product product) {
        var entity = productPersistenceMapper.toEntity(product);
        var savedEntity = productJpaRepository.saveAndFlush(entity);
        return productPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id)
                .map(productPersistenceMapper::toDomain);
    }

}
