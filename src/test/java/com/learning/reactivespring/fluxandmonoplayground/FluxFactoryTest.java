package com.learning.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

/**
 * @author Abhishyam.c on 02/08/20
 */
public class FluxFactoryTest {

    List<String> names = Arrays.asList("chiru", "nag", "venky", "balayya");

    /**
     * Crating a flux from list
     */
    @Test
    public void fluxUsingIterator(){
        Flux<String> namesFlux = Flux.fromIterable(names);
        StepVerifier.create(namesFlux.log())
                .expectNext("chiru", "nag", "venky", "balayya")
                .verifyComplete();
    }

    /**
     * Create a flux from array
     */
    @Test
    public void fluxUsingArray(){
        String[] names = new String[]{"chiru", "nag", "venky", "balayya"};
        Flux<String> namesFlux = Flux.fromArray(names);
        StepVerifier.create(namesFlux)
                .expectNext("chiru", "nag", "venky", "balayya")
                .verifyComplete();
    }
}
