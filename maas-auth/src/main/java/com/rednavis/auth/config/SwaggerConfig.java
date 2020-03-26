package com.rednavis.auth.config;

import com.rednavis.auth.controller.TestController;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Maas AUTH",
        description = "MAAS-AUTH REST API description",
        version = "v1",
        license = @License(
            name = "GNU General Public License v3",
            url = "https://www.gnu.org/licenses/gpl-3.0.html"
        )
    ))
public class SwaggerConfig {

  private static final String[] PACKAGED_TO_MATCH = {TestController.class
      .getPackage()
      .getName()};

  /**
   * authOpenApi.
   *
   * @return
   */
  @Bean
  public GroupedOpenApi authOpenApi() {
    return GroupedOpenApi.builder()
        .setGroup("auth")
        .pathsToMatch("/**")
        .packagesToScan(PACKAGED_TO_MATCH)
        .build();
  }
}
