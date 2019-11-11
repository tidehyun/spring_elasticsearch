package com.example.demo.handler;

import com.example.demo.model.TestModel;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TestHandler {

    @Autowired
    private TestService testService;

    public Mono<ServerResponse> checkIn(ServerRequest request) {
        return request.bodyToMono(TestModel.class).flatMap(model -> {
            testService.checkIn(model).subscribe();
            return ServerResponse.ok().body(Mono.just("SUCCESS"), String.class);
        });
    }

    public Mono<ServerResponse> searchPlace(ServerRequest request) {
        return request.bodyToMono(TestModel.class).flatMap(model -> {
            testService.searchPlace(model).subscribe();
            return ServerResponse.ok().body(Mono.just("SUCCESS"), String.class);
        });
    }

    public Mono<ServerResponse> question(ServerRequest request) {
        return request.bodyToMono(TestModel.class).flatMap(model -> {
            testService.question(model).subscribe();
            return ServerResponse.ok().body(Mono.just("SUCCESS"), String.class);
        });
    }
}
