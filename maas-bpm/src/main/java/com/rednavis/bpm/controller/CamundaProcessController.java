package com.rednavis.bpm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.rednavis.bpm.service.CamundaProcessService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.rest.dto.repository.ProcessDefinitionDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CamundaProcessController {

  //TODO Please, update README file
  private final CamundaProcessService camundaProcessService;

  /**
   * test.
   *
   * @param param param
   * @return
   */
  //TODO Remove this method
  @GetMapping("/test")
  public ProcessInstanceDto test(@RequestParam(defaultValue = "default") String param) {
    List<ProcessDefinition> allProcesses = camundaProcessService.getAllProcesses();
    ProcessDefinition processDefinition = allProcesses.get(0);
    Map<String, Object> params = new HashMap<>();
    params.put("stringParameter", param);
    return ProcessInstanceDto
        .fromProcessInstance(camundaProcessService.startProcessInstanceWithVariables(processDefinition.getId(), params));
  }

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
  // TODO Implement parsing of user from token and adding him ro parameters map
  @PostMapping("/process/{id}")
  public ProcessInstanceDto startProcess(@PathVariable String id,
      /* @RequestHeader("Authentication") String jwtToken, */
      @RequestBody(required = false) Map<String, Object> params) {
    /* String user = getUserFromToken(jwtToken);
    *  params.put("requestBy", user); */
    return ProcessInstanceDto.fromProcessInstance(camundaProcessService.startProcessInstanceWithVariables(id, params));
  }

  //TODO Remove this method
  @GetMapping("/test1")
  public ResponseEntity<String> test1() {
    return ResponseEntity.ok("Hello World");
  }
}