package eu.possible_x.edc_orchestrator.service;

import eu.possible_x.edc_orchestrator.entities.edc.asset.ionoss3extension.IonosS3DataAddress;
import eu.possible_x.edc_orchestrator.entities.edc.catalog.CatalogRequest;
import eu.possible_x.edc_orchestrator.entities.edc.catalog.DcatDataset;
import eu.possible_x.edc_orchestrator.entities.edc.common.IdResponse;
import eu.possible_x.edc_orchestrator.entities.edc.negotiation.ContractNegotiation;
import eu.possible_x.edc_orchestrator.entities.edc.negotiation.ContractOffer;
import eu.possible_x.edc_orchestrator.entities.edc.negotiation.NegotiationInitiateRequest;
import eu.possible_x.edc_orchestrator.entities.edc.transfer.IonosS3TransferProcess;
import eu.possible_x.edc_orchestrator.entities.edc.transfer.TransferRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

  private final EdcClient edcClient;

  public ConsumerService(@Autowired EdcClient edcClient) {
    this.edcClient = edcClient;
  }

  public IonosS3DataAddress acceptContractOffer() {
    String counterPartyAddress = "policyId_14885ea9-aeaf-4f18-8ee7-cdf7d2e251bb";

    // query catalog (take first entry)
    CatalogRequest catalogRequest = CatalogRequest.builder()
            .counterPartyAddress(counterPartyAddress)
            .build();
    log.info("Query Catalog with Request {}", catalogRequest);
    DcatDataset dataset = edcClient.queryCatalog(catalogRequest).getDataset().get(0);

    // initiate negotiation
    NegotiationInitiateRequest negotiationInitiateRequest = NegotiationInitiateRequest.builder()
            .counterPartyAddress(counterPartyAddress)
            .offer(ContractOffer.builder()
                    .offerId(dataset.getHasPolicy().get(0).getId())
                    .assetId(dataset.getAssetId())
                    .policy(dataset.getHasPolicy().get(0))
                    .build())
            .build();
    log.info("Initiate Negotiation with Request {}", negotiationInitiateRequest);
    IdResponse negotiation = edcClient.negotiateOffer(negotiationInitiateRequest);

    // wait until FINALIZED
    ContractNegotiation contractNegotiation = edcClient.checkOfferStatus(negotiation.getId());
    //TODO wait for status FINALIZED
    log.info("Negotiation {}", contractNegotiation);

    // initiate transfer
    TransferRequest transferRequest = TransferRequest.builder()
            .counterPartyAddress(counterPartyAddress)
            .contractId(contractNegotiation.getContractAgreementId())
            .build();
    log.info("Initiate Transfer {}", transferRequest);
    IdResponse transfer = edcClient.initiateTransfer(transferRequest);

    // wait until COMPLETED
    IonosS3TransferProcess transferProcess = edcClient.checkTransferStatus(transfer.getId());
    //TODO wait for status COMPLETED
    log.info("Transfer Process {}", transferProcess);

    return transferProcess.getDataDestination();
  }
}
