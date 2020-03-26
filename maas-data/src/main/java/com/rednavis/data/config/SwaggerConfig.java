package com.rednavis.data.config;

import com.rednavis.data.controller.TestController;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Maas DATA",
        description = "MAAS-DATA REST API description",
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
  public GroupedOpenApi dataOpenApi() {
    return GroupedOpenApi.builder()
        .setGroup("data")
        .pathsToMatch("/**")
        .packagesToScan(PACKAGED_TO_MATCH)
        .build();
  }
}
