package com.learning.reactivespring.router;

import com.learning.reactivespring.handler.SampleHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * @author Abhishyam.c on 02/08/20
 */
@Configuration
public class RouterFunctionConfig {
    @Bean
    public RouterFunction<ServerResponse> route(SampleHandlerFunction sampleHandlerFunction){
        return RouterFunctions
                .route(GET("functional/flux").and(accept(MediaType.APPLICATION_JSON)),
                        sampleHandlerFunction::process);
    }
}
