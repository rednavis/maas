package com.rednavis.bpm.config;

import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestConfig {

  /**
   * restTemplate.
   *
   * @param builder builder
   * @return
   */
  @Bean
  @LoadBalanced
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder
        .requestFactory(new RequestFactorySupplier())
        .build();
  }

  //https://github.com/spring-projects/spring-framework/issues/21321
  class RequestFactorySupplier implements Supplier<ClientHttpRequestFactory> {

    @Override
    public ClientHttpRequestFactory get() {
      return new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }
  }
}
