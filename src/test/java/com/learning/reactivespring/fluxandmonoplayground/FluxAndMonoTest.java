package com.learning.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

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


    @Test
    public void testFluxElementsSequenceWithOnComplete(){
        Flux<String> stringFlux = Flux
                .just("Spring", "Spring Boot", "Reactive Spring")
                .log();
        StepVerifier
                .create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete();
    }

    @Test
    public void testFluxElementsSequenceWithException(){
        Flux<String> stringFlux = Flux
                .just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(
                        Flux.error(new RuntimeException("Exception Created by me"))
                )
                .log();
        StepVerifier
                .create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void testFluxElementsSequenceWithException1(){
        Flux<String> stringFlux = Flux
                .just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(
                        Flux.error(new RuntimeException("Exception Created by me"))
                )
                .log();
        StepVerifier
                .create(stringFlux)
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void testFluxElementsSequenceWithCount(){
        Flux<String> stringFlux = Flux
                .just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(
                        Flux.error(new RuntimeException("Exception Created by me"))
                )
                .log();
        StepVerifier
                .create(stringFlux)
                .expectNextCount(3)
                .expectError(RuntimeException.class)
                .verify();
    }
}
