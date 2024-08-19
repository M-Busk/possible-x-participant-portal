package eu.possible_x.edc_orchestrator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.possible_x.edc_orchestrator.entities.edc.asset.AssetCreateRequest;
import eu.possible_x.edc_orchestrator.entities.edc.asset.AssetProperties;
import eu.possible_x.edc_orchestrator.entities.edc.asset.DataAddress;
import eu.possible_x.edc_orchestrator.entities.edc.asset.ionoss3extension.IonosS3DataSource;
import eu.possible_x.edc_orchestrator.entities.edc.common.IdResponse;
import eu.possible_x.edc_orchestrator.entities.edc.contractdefinition.ContractDefinitionCreateRequest;
import eu.possible_x.edc_orchestrator.entities.edc.contractdefinition.Criterion;
import eu.possible_x.edc_orchestrator.entities.edc.policy.Policy;
import eu.possible_x.edc_orchestrator.entities.edc.policy.PolicyCreateRequest;
import eu.possible_x.edc_orchestrator.entities.fh.catalog.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Service
@Slf4j
public class ProviderService {

    private final EdcClient edcClient;
    private final FhCatalogClient fhCatalogClient;

    @Value("${fh.catalog-secret-key}")
    private String fhCatalogSecretKey;

    public ProviderService(@Autowired EdcClient edcClient, @Autowired FhCatalogClient fhCatalogClient) {
        this.edcClient = edcClient;
        this.fhCatalogClient = fhCatalogClient;
    }

    public IdResponse createOffer() {

        createDatasetEntryInFhCatalog("test-provider");

        DataAddress dataAddress = IonosS3DataSource.builder()
                .bucketName("dev-provider-edc-bucket-possible-31952746")
                .blobName("ssss.txt")
                .keyName("ssss.txt")
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

    public String createDatasetEntryInFhCatalog(String cat_name) {
        ObjectMapper om = new ObjectMapper();
        DatasetToCatalogRequest datasetToCatalogRequest = DatasetToCatalogRequest.builder()
                .graphElements(List.of(
                        GraphFirstElement.builder()
                                .id("https://piveau.io/set/distribution/6c2122e6-59d6-4342-ada9-a2f336450add")
                                .type("dcat:Distribution")
                                .title("my_file.pdf")
                                .accessURL(AccessURL.builder()
                                        .id("http://85.215.193.145:9192/my_access_url")
                                        .build())
                                .build(),
                        GraphSecondElement.builder()
                                .framework(GaxTrustFramework.builder()
                                        .id("https://provider-edc-url")
                                        .build())
                                .title(DctTitle.builder()
                                        .language("en")
                                        .value("cengizTestTitle")
                                        .build())
                                .distribution(DcatDistribution.builder()
                                        .id("https://piveau.io/set/distribution/6c2122e6-59d6-4342-ada9-a2f336450add")
                                        .build())
                                .description(DctDescription.builder()
                                        .language("en")
                                        .value("asdfgh")
                                        .build())
                                .legal("Legal Stuff")
                                .build()))
                .build();

        String value_type = "identifiers";
        Map<String, String> auth = Map.of(
                "Content-Type", "application/json",
                "Authorization", fhCatalogSecretKey);
        log.info("Adding Dataset to Fraunhofer Catalog {}", datasetToCatalogRequest);
        String response = fhCatalogClient.addDatasetToFhCatalog(auth, datasetToCatalogRequest, cat_name, value_type);
        log.info("Response: {}", response);
        return response;
    }
}
