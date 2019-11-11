package com.example.demo.route;

import com.example.demo.handler.TestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class TestRoute implements WebFluxConfigurer {
    @Bean
    public RouterFunction<ServerResponse> restRoutes(TestHandler handler) {
        return RouterFunctions.route(RequestPredicates.POST("/checkIn"), handler::checkIn)
                .andRoute(RequestPredicates.POST("/searchPlace"), handler::searchPlace)
                .andRoute(RequestPredicates.POST("/question"), handler::question);
    }
}
