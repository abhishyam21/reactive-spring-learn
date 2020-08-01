package com.learning.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author Abhishyam.c on 01/08/20
 */
public class MonoTest {

    @Test
    public void testMonoOnComplete(){
        Mono<String> stringMono = Mono.just("Spring");
        StepVerifier
                .create(stringMono.log())
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void testMonoError(){
        Mono<String> stringMono = Mono.error(new RuntimeException("Custom exception"));
        StepVerifier
                .create(stringMono.log())
                .expectError(RuntimeException.class)
                .verify();
    }
}
