package com.ecommerce.gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component

public class JwtGatewayFilter implements GatewayFilter, Ordered {
    private final JwtUtil jwtUtil;
    private static final List<String> PUBLIC_URLS=List.of("/api/v1/users/register","/api/v1/users/login");
    public JwtGatewayFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (PUBLIC_URLS.stream().anyMatch(path::contains)) {
            return chain.filter(exchange);
        }
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }
        String token = authHeader.substring(7);

        if( ! jwtUtil.validateToken(token) ){
            return unauthorized(exchange);
        }

        String userId =  jwtUtil.getUserId(token);
        String  role   =  jwtUtil.getRole(token);

        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().header("X-User-Id", userId).header("X-Role", role).build();


        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }
    private Mono<Void> unauthorized(ServerWebExchange exchange) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }
}
