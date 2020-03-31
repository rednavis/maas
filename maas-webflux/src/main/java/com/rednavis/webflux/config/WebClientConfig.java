package com.rednavis.webflux.config;

import static com.rednavis.webflux.util.MicroserviceUriUtil.MAAS_AUTH_MICROSERVICE;
import static com.rednavis.webflux.util.MicroserviceUriUtil.MAAS_DATA_MICROSERVICE;
import static com.rednavis.webflux.util.WebClientUtils.createWebClient;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  /**
   * dataWebClient.
   *
   * @return
   */
  @LoadBalanced
  @Bean(name = "dataWebClient")
  public WebClient.Builder dataWebClient() {
    return createWebClient(MAAS_DATA_MICROSERVICE);
  }

  /**
   * authWebClient.
   *
   * @return
   */
  @LoadBalanced
  @Bean(name = "authWebClient")
  public WebClient.Builder authWebClient() {
    return createWebClient(MAAS_AUTH_MICROSERVICE);
  }
}
