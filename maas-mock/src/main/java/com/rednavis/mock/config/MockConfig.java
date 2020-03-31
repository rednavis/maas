package com.rednavis.mock.config;

import com.rednavis.mock.service.UserMockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "eureka.enabled", havingValue = "true")
@RequiredArgsConstructor
public class MockConfig implements ApplicationListener<ApplicationReadyEvent> {

  private final UserMockService userMockService;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    log.info("Generate Mock Data...");
    userMockService.mock();
  }
}