package eu.possible_x.edc_orchestrator.service;

import eu.possible_x.edc_orchestrator.entities.edc.asset.AssetCreateRequest;
import eu.possible_x.edc_orchestrator.entities.edc.common.IdResponse;
import eu.possible_x.edc_orchestrator.entities.edc.contractdefinition.ContractDefinitionCreateRequest;
import eu.possible_x.edc_orchestrator.entities.edc.policy.PolicyCreateRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface EdcClient {
    @PostExchange("/v3/assets")
    IdResponse createAsset(@RequestBody AssetCreateRequest assetCreateRequest);

    @PostExchange("/v2/policydefinitions")
    IdResponse createPolicy(@RequestBody PolicyCreateRequest policyCreateRequest);

    @PostExchange("/v2/contractdefinitions")
    IdResponse createContractDefinition(@RequestBody ContractDefinitionCreateRequest contractDefinitionCreateRequest);
}

