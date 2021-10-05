package pl.training.payments;

import org.mapstruct.factory.Mappers;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import pl.training.commons.money.FastMoneyMapper;
import pl.training.commons.time.SystemTimeProvider;
import pl.training.commons.time.TimeProvider;
import pl.training.payments.domain.PaymentRepository;
import pl.training.payments.domain.PaymentService;
import pl.training.payments.domain.PaymentServiceFactory;
import pl.training.payments.domain.PaymentStatusChangeNotifier;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableBinding(Source.class)
@EnableSwagger2
@EnableAspectJAutoProxy
@Configuration
class PaymentsConfiguration {

    @Bean
    Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pl.training.payments"))
                .build();
    }

    @Bean
    FastMoneyMapper fastMoneyMapper() {
        return Mappers.getMapper(FastMoneyMapper.class);
    }

    @Bean
    TimeProvider timeProvider() {
        return new SystemTimeProvider();
    }

    @Bean
    PaymentService paymentService(TimeProvider timeProvider, PaymentRepository paymentRepository, PaymentStatusChangeNotifier paymentStatusChangeNotifier) {
        return new PaymentServiceFactory().create(timeProvider, paymentRepository, paymentStatusChangeNotifier);
    }

}
