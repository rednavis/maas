package com.rednavis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MaasConfigApplication {

  public static void main(String... args) {
    SpringApplication.run(MaasConfigApplication.class, args);
  }
}
