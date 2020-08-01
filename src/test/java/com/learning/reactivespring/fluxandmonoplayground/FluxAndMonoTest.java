package com.learning.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

/**
 * @author Abhishyam.c on 01/08/20
 */

public class FluxAndMonoTest {

    @Test
    public void testFlux(){
        Flux<String> stringFlux = Flux
                .just("Spring", "Spring Boot", "Reactive Spring")
                .log();
        stringFlux
                .subscribe(System.out::println);
    }

    @Test
    public void testFluxWithException(){
        Flux<String> stringFlux = Flux
                .just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(
                        Flux.error(new RuntimeException("Exception Created by me"))
                )
                .log();
        stringFlux
                .subscribe(System.out::println,
                        System.out::println);
    }
}
