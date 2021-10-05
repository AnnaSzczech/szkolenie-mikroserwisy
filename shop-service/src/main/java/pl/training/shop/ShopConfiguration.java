package pl.training.shop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.training.commons.money.LocalMoney;
import pl.training.shop.commons.RestTemplateTokenInterceptor;
import pl.training.shop.orders.domain.OrderFeeCalculator;
import pl.training.shop.orders.domain.OrderRepository;
import pl.training.shop.orders.domain.OrderService;
import pl.training.shop.payments.domain.PaymentService;
import pl.training.shop.products.domain.Product;
import pl.training.shop.products.domain.ProductRepository;
import pl.training.shop.products.domain.ProductService;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableBinding(Sink.class)
@EnableFeignClients("pl.training.payments")
@EnableSwagger2
@Configuration
public class ShopConfiguration {

    @Bean
    Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pl.training.shop"))
                .build();
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("products");
    }

    @Bean
    ProductService productService(ProductRepository productRepository) {
        var productService = new ProductService(productRepository);
        productService.save(new Product("Spring in action", LocalMoney.of(200)));
        productService.save(new Product("Cloud in action", LocalMoney.of(400)));
        return productService;
    }

    @RefreshScope
    @Bean
    OrderFeeCalculator orderFeeCalculator(@Value("${order-fee}") long orderFess) {
        var calculator = new OrderFeeCalculator();
        calculator.setValue(orderFess);
        return calculator;
    }

    @Bean
    OrderService orderService(OrderRepository orderRepository, PaymentService paymentService, OrderFeeCalculator orderFeeCalculator) {
        return new OrderService(orderRepository, paymentService, orderFeeCalculator);
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .interceptors(List.of(new RestTemplateTokenInterceptor()))
                .build();
    }

}