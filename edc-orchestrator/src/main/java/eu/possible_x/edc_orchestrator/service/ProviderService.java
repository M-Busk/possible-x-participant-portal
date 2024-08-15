package eu.possible_x.edc_orchestrator.service;

import eu.possible_x.edc_orchestrator.entities.edc.asset.AssetCreateRequest;
import eu.possible_x.edc_orchestrator.entities.edc.asset.AssetProperties;
import eu.possible_x.edc_orchestrator.entities.edc.asset.DataAddress;
import eu.possible_x.edc_orchestrator.entities.edc.asset.ionoss3extension.IonosS3DataSource;
import eu.possible_x.edc_orchestrator.entities.edc.common.IdResponse;
import eu.possible_x.edc_orchestrator.entities.edc.contractdefinition.ContractDefinitionCreateRequest;
import eu.possible_x.edc_orchestrator.entities.edc.contractdefinition.Criterion;
import eu.possible_x.edc_orchestrator.entities.edc.policy.Policy;
import eu.possible_x.edc_orchestrator.entities.edc.policy.PolicyCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProviderService {

    private final EdcClient edcClient;

    public ProviderService(@Autowired EdcClient edcClient) {
        this.edcClient = edcClient;
    }

    public IdResponse createOffer() {

        DataAddress dataAddress = IonosS3DataSource.builder()
                .bucketName("dev-provider-edc-bucket-possible-31952746")
                .blobName("ssss.txt")
                .keyName("device1-data.csv")
                .storage("s3-eu-central-2.ionoscloud.com")
                .build();

        // create asset
        String assetId = "assetId_" + UUID.randomUUID();
        AssetCreateRequest assetCreateRequest = AssetCreateRequest.builder()
                .id(assetId)
                .properties(AssetProperties.builder()
                        .name("assetName")
                        .description("assetDescription")
                        .version("assetVersion")
                        .contenttype("application/json")
                        .build())
                .dataAddress(dataAddress)
                .build();

        log.info("Creating Asset {}", assetCreateRequest);
        IdResponse assetIdResponse = edcClient.createAsset(assetCreateRequest);

        // create policy
        String policyId = "policyId_" + UUID.randomUUID();
        PolicyCreateRequest policyCreateRequest = PolicyCreateRequest.builder()
                .id(policyId)
                .policy(Policy.builder()
                        .id(policyId)
                        .obligation(Collections.emptyList())
                        .prohibition(Collections.emptyList())
                        .permission(Collections.emptyList())
                        .build())
                .build();
        log.info("Creating Policy {}", policyCreateRequest);
        IdResponse policyIdResponse = edcClient.createPolicy(policyCreateRequest);

        // create contract definition
        String contractDefinitionId = "contractDefinitionId_" + UUID.randomUUID();
        ContractDefinitionCreateRequest contractDefinitionCreateRequest = ContractDefinitionCreateRequest.builder()
                .id(contractDefinitionId)
                .contractPolicyId(policyIdResponse.getId())
                .accessPolicyId(policyIdResponse.getId())
                .assetsSelector(List.of(Criterion.builder()
                        .operandLeft("https://w3id.org/edc/v0.0.1/ns/id")
                        .operator("=")
                        .operandRight(assetId)
                        .build()))
                .build();
        log.info("Creating Contract Definition {}", contractDefinitionCreateRequest);
        return edcClient.createContractDefinition(contractDefinitionCreateRequest);
    }
}
