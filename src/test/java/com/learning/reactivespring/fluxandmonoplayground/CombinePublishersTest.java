package com.learning.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * @author Abhishyam.c on 02/08/20
 */
public class CombinePublishersTest {

    @Test
    public void combineUsingMerge(){
        Flux<String> flux1 = Flux.just("a", "b", "c");
        Flux<String> flux2 = Flux.just("1", "2", "3");
        Flux<String> combinesFlux = Flux.merge(flux1, flux2);

        StepVerifier.create(combinesFlux.log())
                .expectSubscription()
                .expectNext("a","b","c","1","2","3")
                .verifyComplete();

    }

    /**
     * Here we are not going to maintain the order of the elements
     * So we are asserting on count.
     */
    @Test
    public void combineUsingMergeWithDelay(){
        Flux<String> flux1 = Flux.just("a", "b", "c").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("1", "2", "3").delayElements(Duration.ofSeconds(1));
        Flux<String> combinesFlux = Flux.merge(flux1, flux2);

        StepVerifier.create(combinesFlux.log())
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();

    }

    @Test
    public void combineUsingConcat(){
        Flux<String> flux1 = Flux.just("a", "b", "c");
        Flux<String> flux2 = Flux.just("1", "2", "3");
        Flux<String> concatFlux = Flux.concat(flux1, flux2);

        StepVerifier.create(concatFlux.log())
                .expectSubscription()
                .expectNext("a","b","c","1","2","3")
                .verifyComplete();
    }

    @Test
    public void combineUsingZip(){
        Flux<String> flux1 = Flux.just("a", "b", "c");
        Flux<String> flux2 = Flux.just("1", "2", "3");
        Flux<String> concatFlux = Flux.zip(flux1, flux2, (f1,f2) -> f1.concat("-"+f2));

        StepVerifier.create(concatFlux.log())
                .expectSubscription()
                .expectNext("a-1","b-2","c-3")
                .verifyComplete();
    }
}
