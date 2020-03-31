package com.rednavis.mock;

import com.rednavis.webflux.WebFluxModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebFluxModule.class)
public class MaasMockApplication {

  public static void main(String... args) {
    SpringApplication.run(MaasMockApplication.class, args);
  }
}
