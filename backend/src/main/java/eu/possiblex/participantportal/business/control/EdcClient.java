/*
 *  Copyright 2024 Dataport. All rights reserved. Developed as part of the MERLOT project.
 *  Copyright 2024-2025 Dataport. All rights reserved. Extended as part of the POSSIBLE project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Modifications:
 * - Dataport (part of the POSSIBLE project) - 14 August, 2024 - Adjust package names and imports
 */

package eu.possiblex.participantportal.business.control;

import eu.possiblex.participantportal.business.entity.edc.asset.AssetCreateRequest;
import eu.possiblex.participantportal.business.entity.edc.asset.possible.PossibleAsset;
import eu.possiblex.participantportal.business.entity.edc.catalog.CatalogRequest;
import eu.possiblex.participantportal.business.entity.edc.catalog.DcatCatalog;
import eu.possiblex.participantportal.business.entity.edc.catalog.QuerySpec;
import eu.possiblex.participantportal.business.entity.edc.common.IdResponse;
import eu.possiblex.participantportal.business.entity.edc.contractagreement.ContractAgreement;
import eu.possiblex.participantportal.business.entity.edc.contractdefinition.ContractDefinitionCreateRequest;
import eu.possiblex.participantportal.business.entity.edc.negotiation.ContractNegotiation;
import eu.possiblex.participantportal.business.entity.edc.negotiation.NegotiationInitiateRequest;
import eu.possiblex.participantportal.business.entity.edc.policy.PolicyCreateRequest;
import eu.possiblex.participantportal.business.entity.edc.transfer.AWSS3TransferProcess;
import eu.possiblex.participantportal.business.entity.edc.transfer.TerminateTransferRequest;
import eu.possiblex.participantportal.business.entity.edc.transfer.TransferRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

public interface EdcClient {
    @PostExchange("/v3/assets")
    IdResponse createAsset(@RequestBody AssetCreateRequest assetCreateRequest);

    @PostExchange("/v3/policydefinitions")
    IdResponse createPolicy(@RequestBody PolicyCreateRequest policyCreateRequest);

    @PostExchange("/v3/contractdefinitions")
    IdResponse createContractDefinition(@RequestBody ContractDefinitionCreateRequest contractDefinitionCreateRequest);

    @PostExchange("/v3/catalog/request")
    DcatCatalog queryCatalog(@RequestBody CatalogRequest catalogRequest);

    @PostExchange("/v3/contractnegotiations")
    IdResponse negotiateOffer(@RequestBody NegotiationInitiateRequest negotiationInitiateRequest);

    @GetExchange("/v3/contractnegotiations/{negotiationId}")
    ContractNegotiation checkOfferStatus(@PathVariable String negotiationId);

    @PostExchange("/v3/transferprocesses")
    IdResponse initiateTransfer(@RequestBody TransferRequest transferRequest);

    @GetExchange("/v3/transferprocesses/{transferId}")
    AWSS3TransferProcess checkTransferStatus(@PathVariable String transferId);

    @PostExchange("/v3/transferprocesses/{transferId}/deprovision")
    void deprovisionTransfer(@PathVariable String transferId);

    @PostExchange("/v3/transferprocesses/{transferId}/terminate")
    void terminateTransfer(@PathVariable String transferId, @RequestBody TerminateTransferRequest request);

    @DeleteExchange("/v3/contractdefinitions/{contractDefinitionId}")
    void revokeContractDefinition(@PathVariable String contractDefinitionId);

    @PostExchange("/v3/contractagreements/request")
    List<ContractAgreement> queryContractAgreements(@RequestBody QuerySpec querySpec);

    @GetExchange("/v3/contractagreements/{contractAgreementId}")
    ContractAgreement getContractAgreementById(@PathVariable String contractAgreementId);

    @PostExchange("/v3/assets/request")
    List<PossibleAsset> queryPossibleAssets();
}

