package com.rednavis.sd.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEurekaServer
@ConditionalOnProperty(name = "eureka.enabled", havingValue = "true")
public class EurekaConfig {

}
