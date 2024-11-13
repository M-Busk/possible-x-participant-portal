package eu.possiblex.participantportal.business.control;

import eu.possiblex.participantportal.business.entity.edc.CreateEdcOfferBE;
import eu.possiblex.participantportal.business.entity.edc.asset.AssetCreateRequest;
import eu.possiblex.participantportal.business.entity.edc.asset.DataAddress;
import eu.possiblex.participantportal.business.entity.edc.asset.ionoss3extension.IonosS3DataSource;
import eu.possiblex.participantportal.business.entity.edc.common.IdResponse;
import eu.possiblex.participantportal.business.entity.edc.contractdefinition.ContractDefinitionCreateRequest;
import eu.possiblex.participantportal.business.entity.edc.contractdefinition.Criterion;
import eu.possiblex.participantportal.business.entity.edc.policy.Policy;
import eu.possiblex.participantportal.business.entity.edc.policy.PolicyCreateRequest;

import java.util.List;
import java.util.UUID;

/**
 * The ProviderRequestBuilder class is responsible for constructing various request objects required for creating offers
 * in the FH catalog and EDC.
 */
public class ProviderRequestBuilder {

    private final CreateEdcOfferBE createEdcOfferBE;

    /**
     * Constructor for ProviderRequestBuilder.
     *
     * @param createEdcOfferBE the EDC offer business entity
     */
    public ProviderRequestBuilder(CreateEdcOfferBE createEdcOfferBE) {

        this.createEdcOfferBE = createEdcOfferBE;
    }

    /**
     * Builds an AssetCreateRequest for the EDC offer.
     *
     * @return the AssetCreateRequest
     */
    public AssetCreateRequest buildAssetRequest(String bucketName, String bucketStorageRegion) {

        DataAddress dataAddress = IonosS3DataSource.builder().bucketName(bucketName)
            .blobName(createEdcOfferBE.getFileName()).keyName(createEdcOfferBE.getFileName())
            .region(bucketStorageRegion).build();

        return AssetCreateRequest.builder().id(createEdcOfferBE.getAssetId())
            .properties(createEdcOfferBE.getProperties()).dataAddress(dataAddress).build();
    }

    /**
     * Builds a PolicyCreateRequest for the EDC offer.
     *
     * @return the PolicyCreateRequest
     */
    public PolicyCreateRequest buildPolicyRequest() {

        return buildPolicyRequest(createEdcOfferBE.getPolicy());
    }

    /**
     * Builds a PolicyCreateRequest for a given policy for the EDC offer.
     *
     * @param policy the policy
     * @return the PolicyCreateRequest
     */
    public PolicyCreateRequest buildPolicyRequest(Policy policy) {

        return PolicyCreateRequest.builder().id(UUID.randomUUID().toString()).policy(policy).build();
    }

    /**
     * Builds a ContractDefinitionCreateRequest for the EDC offer.
     *
     * @param accessPolicyIdResponse the policy ID response for the access policy
     * @param contractPolicyIdResponse the policy ID response for the contract policy
     * @param assetIdResponse the asset ID response
     * @return the ContractDefinitionCreateRequest
     */
    public ContractDefinitionCreateRequest buildContractDefinitionRequest(IdResponse accessPolicyIdResponse,
        IdResponse contractPolicyIdResponse, IdResponse assetIdResponse) {

        return ContractDefinitionCreateRequest.builder().id(UUID.randomUUID().toString())
            .contractPolicyId(contractPolicyIdResponse.getId()).accessPolicyId(accessPolicyIdResponse.getId())
            .assetsSelector(List.of(Criterion.builder().operandLeft("https://w3id.org/edc/v0.0.1/ns/id").operator("=")
                .operandRight(assetIdResponse.getId()).build())).build();
    }
}