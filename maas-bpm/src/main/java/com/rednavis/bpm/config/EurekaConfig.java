package com.rednavis.bpm.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEurekaClient
@ConditionalOnProperty(name = "eureka.enabled", havingValue = "true")
public class EurekaConfig {

}
