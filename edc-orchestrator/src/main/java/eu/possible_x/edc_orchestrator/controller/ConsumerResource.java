package eu.possible_x.edc_orchestrator.controller;


import eu.possible_x.edc_orchestrator.entities.edc.asset.ionoss3extension.IonosS3DataAddress;
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
   * POST endpoint to accept a Contract Offer
   *
   * @return Data Address of the transferred data
   */
  @PostMapping(value = "/acceptContractOffer", produces = MediaType.APPLICATION_JSON_VALUE)
  public String acceptContractOffer() {
    IonosS3DataAddress dataAddress = consumerService.acceptContractOffer();
    return "{\"dataAddress\": \"" + dataAddress + "\"}";
  }
}
