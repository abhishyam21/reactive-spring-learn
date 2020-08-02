package com.learning.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * @author Abhishyam.c on 02/08/20
 */
public class FluxErrorHandlingTest {

    @Test
    public void fluxErrorHandling(){
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception occured")))
                .concatWith(Flux.just("D"));

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectError(RuntimeException.class)
                .verify();
    }


    @Test
    public void fluxErrorHandlingWithResume(){
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception occured")))
                .concatWith(Flux.just("D"))
                .onErrorResume(e ->{
                    System.out.println("Exception is :"+ e);
                    return Flux.just("default");
                });

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    public void fluxErrorHandlingWithReturn(){
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception occured")))
                .concatWith(Flux.just("D"))
                .onErrorReturn("default");

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    public void fluxErrorHandlingWithErrorMap(){
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception occured")))
                .concatWith(Flux.just("D"))
                .onErrorMap(CustomException::new);

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectError(CustomException.class)
                .verify();
    }

    @Test
    public void fluxErrorHandlingWithErrorMapRetry(){
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception occured")))
                .concatWith(Flux.just("D"))
                .onErrorMap(CustomException::new)
                .retry(2);

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectError(CustomException.class)
                .verify();
    }


    private class CustomException extends Throwable {
        public CustomException(Throwable e) {
            super(e);
        }

        public CustomException(String msg){
            super(msg);
        }
    }
}
