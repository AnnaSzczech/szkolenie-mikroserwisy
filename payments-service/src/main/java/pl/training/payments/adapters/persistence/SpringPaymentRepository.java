package pl.training.payments.adapters.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.training.payments.domain.Payment;
import pl.training.payments.domain.PaymentRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class SpringPaymentRepository implements PaymentRepository {

    private final PaymentPersistenceMapper paymentPersistenceMapper;
    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(Payment payment) {
        var entity = paymentPersistenceMapper.toEntity(payment);
        var savedEntity = paymentJpaRepository.save(entity);
        return paymentPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Payment> findById(String id) {
        return paymentJpaRepository.findById(id)
                .map(paymentPersistenceMapper::toDomain);
    }

}
