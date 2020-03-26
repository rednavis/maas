package com.rednavis.data.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TestController {

  @GetMapping("/test")
  public Mono<String> test() {
    return Mono.just("Hello World from AUTH");
  }
}
