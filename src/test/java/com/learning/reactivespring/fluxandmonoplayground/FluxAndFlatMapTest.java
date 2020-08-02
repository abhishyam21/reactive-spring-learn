package com.learning.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

/**
 * @author Abhishyam.c on 02/08/20
 */
public class FluxAndFlatMapTest {
    List<String> names = Arrays.asList("A", "B", "C", "D", "E", "F");

    @Test
    public void transformUsingFlatMap(){
        Flux<String> namesFlux = Flux
                .fromIterable(names)
                .flatMap(s -> Flux.fromIterable(crateStringList(s))) //DB or external api call to get Flux<String>
                .log();
        StepVerifier.create(namesFlux)
                .expectNext("A")
                .expectNext("newValue")
                .expectNext("B")
                .expectNext("newValue")
                .expectNext("C")
                .expectNext("newValue")
                .expectNext("D")
                .expectNext("newValue")
                .expectNext("E")
                .expectNext("newValue")
                .expectNext("F")
                .expectNext("newValue")
                .verifyComplete();

    }

    /**
     * The above method waits for 6 seconds to to complete it's task.
     * But that is not the purpose of the reactive programming.
     * We should use the resources very optimistically.
     *
     * So we gonna use parallel calls to complete the task faster.
     *
     */
    @Test
    public void transformUsingFlatMapUsingParallel(){
        Flux<Flux<String>> namesFlux = Flux
                .fromIterable(names) //this will create Flux<String>
                //this will create Flux<Flux<String>>. Which means internally it will be
                //Flux<A,B>, Flux<C,D>, Flux<E,F>
                .window(2);

        Flux<String> stringFlux = namesFlux.flatMap(s ->
                s.map(this::crateStringList).subscribeOn(parallel())
        ).flatMap(Flux::fromIterable).log();

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

    }

    @Test
    public void transformUsingFlatMapParallelAndOrder(){
        Flux<Flux<String>> namesFlux = Flux
                .fromIterable(names) //this will create Flux<String>
                //this will create Flux<Flux<String>>. Which means internally it will be
                //Flux<A,B>, Flux<C,D>, Flux<E,F>
                .window(2);

        Flux<String> stringFlux = namesFlux.flatMapSequential(s ->
                s.map(this::crateStringList).subscribeOn(parallel())
        ).flatMap(Flux::fromIterable).log();

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

    }

    private List<String> crateStringList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s,"newValue");
    }


}
