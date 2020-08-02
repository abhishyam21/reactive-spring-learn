package com.learning.reactivespring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @author Abhishyam.c on 01/08/20
 */

@RestController
public class FluxController {

    @GetMapping("flux")
    public Flux<Integer> returnFluxOfIntegers(){
        return Flux
                .range(1,5)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(value = "flux-stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> returnFluxOfIntegersStream(){
        return Flux
                .range(1,5)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(value = "flux-infinite", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> returnInfiniteFlux(){
        return Flux
                .interval(Duration.ofSeconds(1))
                .log();
    }

}
