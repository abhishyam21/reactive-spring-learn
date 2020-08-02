package com.learning.reactivespring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Abhishyam.c on 02/08/20
 */
@Slf4j
@RestController
public class MonoController {
    @GetMapping("mono")
    public Mono<Integer> getMonoOfSingleElement(){
        return Mono.just(1)
                .log();
    }
}
