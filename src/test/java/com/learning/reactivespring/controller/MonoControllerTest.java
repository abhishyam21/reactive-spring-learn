package com.learning.reactivespring.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author Abhishyam.c on 02/08/20
 */

@ExtendWith(SpringExtension.class)
@WebFluxTest
class MonoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testSingleMono(){
            webTestClient.get()
                    .uri("/mono")
                    .accept(MediaType.APPLICATION_STREAM_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(Integer.class)
                    .consumeWith( response -> {
                        Assertions.assertEquals(new Integer(1), response.getResponseBody());
                    });
    }
}