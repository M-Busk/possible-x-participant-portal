package eu.possible_x.edc_orchestrator.service;

import eu.possible_x.edc_orchestrator.entities.edc.common.IdResponse;
import eu.possible_x.edc_orchestrator.entities.edc.transfer.TransferRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

  private final EdcClient edcClient;

  public ConsumerService(@Autowired EdcClient edcClient) { this.edcClient = edcClient; }

  public IdResponse acceptContractOffer() {
    TransferRequest transferRequest = TransferRequest.builder().build();

    return edcClient.initiateTransfer(transferRequest);
  }
}
