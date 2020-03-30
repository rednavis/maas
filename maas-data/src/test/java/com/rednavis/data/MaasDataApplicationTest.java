package com.rednavis.data;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@Getter
@Testcontainers
@Tag("IntegrationTest")
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false"})
@ContextConfiguration(initializers = {MaasDataApplicationTest.Initializer.class})
public abstract class MaasDataApplicationTest {

  static final MongoDbContainer MONGO_DB_CONTAINER;

  static {
    MONGO_DB_CONTAINER = new MongoDbContainer();
    MONGO_DB_CONTAINER.start();
  }

  @Autowired
  private ApplicationContext context;
  private WebTestClient webTestClient;

  @BeforeEach
  public void setUp() {
    webTestClient = WebTestClient.bindToApplicationContext(context).build();
  }

  static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      log.info("INIT SPRING CONFIG");
      TestPropertyValues.of(
          "spring.data.mongodb.host=" + MONGO_DB_CONTAINER.getContainerIpAddress(),
          "spring.data.mongodb.port=" + MONGO_DB_CONTAINER.getPort()
      ).applyTo(configurableApplicationContext.getEnvironment());
    }
  }
}