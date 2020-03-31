package com.rednavis.webflux.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@UtilityClass
public class WebClientUtils {

  /**
   * createWebClient.
   *
   * @param baseUrl baseUrl
   * @return
   */
  public static WebClient.Builder createWebClient(String baseUrl) {
    return WebClient.builder()
        .baseUrl(baseUrl)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
  }
}
