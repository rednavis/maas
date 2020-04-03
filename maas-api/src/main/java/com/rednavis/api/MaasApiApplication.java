package com.rednavis.api;

import com.rednavis.webflux.WebFluxModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebFluxModule.class)
public class MaasApiApplication {

  public static void main(String... args) {
    SpringApplication.run(MaasApiApplication.class, args);
  }
}
