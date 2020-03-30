package com.rednavis.config.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigServer
@ConditionalOnProperty(name = "eureka.enabled", havingValue = "true")
public class EurekaConfig {

}
