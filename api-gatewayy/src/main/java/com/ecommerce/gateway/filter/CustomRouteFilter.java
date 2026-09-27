package com.ecommerce.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;

public class CustomRouteFilter extends AbstractGatewayFilterFactory<Object> {

    public CustomRouteFilter() {
        super(Object.class);
    }
    @Override
    public GatewayFilter apply(Object config) {
        return ((exchange, chain) ->{
            System.out.println("Route Filter Called : Before Routing");
            exchange.getRequest().mutate().header("X-Route-Header", "Added-By-Gateway").build();
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("Route Filter Called : After Routing");

            }));
        });
    }
}
