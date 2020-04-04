package com.rednavis.vaadin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.rednavis.shared.rest.response.ProcessDefinitionResponse;
import com.rednavis.shared.rest.response.ProcessInstanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BpmService {

  private final RestService restService;

  /**
   * getProcesseId.
   *
   * @param accessToken accessToken
   * @return
   */
  public String getProcesseId(String accessToken) {
    String url = "http://localhost:8081/bpm/processes";
    List<ProcessDefinitionResponse> processDefinitionResponseList = restService
        .getWithToken(url, accessToken, new ParameterizedTypeReference<>() {
        });
    ProcessDefinitionResponse processDefinitionResponse = processDefinitionResponseList.get(0);
    return processDefinitionResponse.getId();
  }

  /**
   * startProcesse.
   *
   * @param accessToken accessToken
   * @param processId   processId
   * @param bookId      bookId
   * @return
   */
  public ProcessInstanceResponse startProcesse(String accessToken, String processId, String bookId) {
    String url = "http://localhost:8081/bpm/process/" + processId;
    Map<String, Object> params = new HashMap<>();
    params.put("bookId", bookId);
    return restService.postWithToken(url, params, accessToken, ProcessInstanceResponse.class);
  }
}
