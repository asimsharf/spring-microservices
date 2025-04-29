package com.sudagoarth.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes().route("user-service", r -> r.path("/user-service/**").filters(f -> f.circuitBreaker(c -> c.setName("userServiceCircuitBreaker").setFallbackUri("forward:/fallback/user-service"))).uri("lb://user-service")).build();
    }
}
