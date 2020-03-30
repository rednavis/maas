package com.rednavis.bpm.config;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.connect.plugin.impl.ConnectProcessEnginePlugin;
import org.camunda.spin.plugin.impl.SpinProcessEnginePlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableProcessApplication
public class CamundaConfig {

  @Bean
  public ConnectProcessEnginePlugin connectProcessEnginePlugin() {
    return new ConnectProcessEnginePlugin();
  }

  @Bean
  public SpinProcessEnginePlugin spinProcessEnginePlugin() {
    return new SpinProcessEnginePlugin();
  }

}
