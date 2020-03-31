package com.rednavis.auth;

import com.rednavis.webflux.WebFluxModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebFluxModule.class)
public class MaasAuthApplication {

  public static void main(String... args) {
    SpringApplication.run(MaasAuthApplication.class, args);
  }
}
