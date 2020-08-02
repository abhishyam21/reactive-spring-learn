package com.learning.reactivespring.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

/**
 * @author Abhishyam.c on 01/08/20
 */
@ExtendWith(SpringExtension.class)
@WebFluxTest
public class FluxControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @Test
    public void testFluxApproach1(){
        Flux<Integer> integerFlux = webTestClient
                .get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier
                .create(integerFlux)
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .verifyComplete();
    }

    @Test
    public void testFluxApproach2(){
        webTestClient
                .get()
                .uri("/flux-stream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .expectBodyList(Integer.class)
                .hasSize(5);
    }

    @Test
    public void testFluxApproach3(){
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
        EntityExchangeResult<List<Integer>> actualResult = webTestClient
                .get()
                .uri("/flux-stream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();
        Assertions.assertEquals(expected,actualResult.getResponseBody());

    }

    @Test
    public void testInfiniteFlux(){
        Flux<Long> fluxOfLong = webTestClient
                .get()
                .uri("/flux-infinite")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();
        StepVerifier
                .create(fluxOfLong)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .expectNext(3L)
                .thenCancel()
                .verify();

    }
}
