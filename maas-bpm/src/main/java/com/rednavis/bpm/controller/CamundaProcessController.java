package com.rednavis.bpm.controller;

import static com.rednavis.shared.util.StringUtils.BEARER_SPACE;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.nimbusds.jwt.SignedJWT;
import com.rednavis.bpm.service.CamundaProcessService;
import com.rednavis.shared.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.rest.dto.repository.ProcessDefinitionDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CamundaProcessController {

  private final CamundaProcessService camundaProcessService;

  /**
   * getProcesses.
   *
   * @return
   */
  @GetMapping("/processes")
  public List<ProcessDefinitionDto> getProcesses() {
    return camundaProcessService.getAllProcesses().stream().map(ProcessDefinitionDto::fromProcessDefinition).collect(Collectors.toList());
  }

  /**
   * startProcess.
   *
   * @param id     id
   * @param params params
   * @return
   */
  @PostMapping("/process/{id}")
  public ProcessInstanceDto startProcess(@PathVariable String id, @RequestHeader("Authorization") String jwtToken,
      @RequestBody(required = false) Map<String, Object> params) {
    Map<String, Object> config = (params == null) ? new HashMap<>() : new HashMap<>(params);
    log.info("PARAMS1: {}", config.entrySet()
        .stream()
        .map(entry -> entry.getKey() + " - " + entry.getValue())
        .collect(Collectors.joining(", ")));
    String token = jwtToken.substring(BEARER_SPACE.length());
    log.info("TOKEN: {}", token);
    String userName = parseToken(token);
    log.info("userName: {}", userName);
    config.put("requestBy", userName);
    config.put("Test1", "Test1");
    config.put("Test2", "Test2");
    config.put("Test3", "Test3");
    config.put("bookId1", "bookId1");
    log.info("PARAMS2: {}", config.entrySet()
        .stream()
        .map(entry -> entry.getKey() + " - " + entry.getValue())
        .collect(Collectors.joining(", ")));
    return ProcessInstanceDto.fromProcessInstance(camundaProcessService.startProcessInstanceWithVariables(id, config));
  }

  @GetMapping("/test")
  public ResponseEntity<String> test1() {
    return ResponseEntity.ok("Hello World");
  }

  private String parseToken(String token) {
    SignedJWT signedJwt;
    try {
      signedJwt = SignedJWT.parse(token);
      return signedJwt.getJWTClaimsSet()
          .getStringClaim(CurrentUser.Fields.userName);
    } catch (ParseException e) {
      throw new RuntimeException("Can't parse token [token: " + token + "]");
    }
  }
}