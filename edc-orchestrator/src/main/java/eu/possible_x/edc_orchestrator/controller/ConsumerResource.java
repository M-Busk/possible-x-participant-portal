package eu.possible_x.edc_orchestrator.controller;


import eu.possible_x.edc_orchestrator.entities.edc.common.IdResponse;
import eu.possible_x.edc_orchestrator.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class ConsumerResource {

  private final ConsumerService consumerService;

  public ConsumerResource(@Autowired ConsumerService consumerService) {
    this.consumerService = consumerService;
  }

  /**
   * POST endpoint to accept a contract offer
   *
   * @return ID of the Response from the EDC
   */
  @PostMapping(value = "/acceptContractOffer", produces = MediaType.APPLICATION_JSON_VALUE)
  public String acceptContractOffer() {
    IdResponse response = consumerService.acceptContractOffer();
    return "{\"id\": \"" + response.getId() + "\"}";
  }
}
