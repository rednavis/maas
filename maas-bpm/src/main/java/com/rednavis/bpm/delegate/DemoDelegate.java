package com.rednavis.bpm.delegate;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) {
    log.info("DemoDelegate start");
    Map<String, Object> variables = execution.getVariables();
    variables.computeIfPresent("default", (key, value) -> value = "modified");
  }
}
