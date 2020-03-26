package com.rednavis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@Slf4j
@SpringBootApplication
@EnableConfigServer
public class MaasConfigApplication {

  public static void main(String... args) {
    SpringApplication.run(MaasConfigApplication.class, args);
  }
}
