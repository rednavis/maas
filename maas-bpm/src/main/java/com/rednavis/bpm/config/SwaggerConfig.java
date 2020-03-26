package com.rednavis.bpm.config;


import com.rednavis.bpm.controller.CamundaProcessController;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Maas BPM",
        description = "MAAS-BPM REST API description",
        version = "v1",
        license = @License(
            name = "GNU General Public License v3",
            url = "https://www.gnu.org/licenses/gpl-3.0.html"
        )
    ))
public class SwaggerConfig {

  private static final String[] PACKAGED_TO_MATCH = {CamundaProcessController.class
      .getPackage()
      .getName()};

  /**
   * bpmOpenApi.
   *
   * @return
   */
  @Bean
  public GroupedOpenApi bpmOpenApi() {
    return GroupedOpenApi.builder()
        .setGroup("bpm")
        //.pathsToMatch(new String[] {BPM_URL_PATTERN})
        .packagesToScan(PACKAGED_TO_MATCH)
        .build();
  }
}
