package com.rednavis.vaadin.property;

import static com.rednavis.shared.util.RestUrlUtils.AUTH_URL_VAADIN;
import static com.rednavis.shared.util.RestUrlUtils.BOOK_URL_VAADIN;
import static com.rednavis.shared.util.RestUrlUtils.USER_URL_VAADIN;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("maas")
public class MaasProperty {

  private Api api;

  public String createAuthUrl(String restPoint) {
    return getApi().getServer() + AUTH_URL_VAADIN + restPoint;
  }

  public String createUserUrl(String restPoint) {
    return getApi().getServer() + "/data" + USER_URL_VAADIN + restPoint;
  }

  public String createBookUrl(String restPoint) {
    return getApi().getServer() + BOOK_URL_VAADIN + restPoint;
  }

  @Data
  public static class Api {

    private String server;
  }
}
