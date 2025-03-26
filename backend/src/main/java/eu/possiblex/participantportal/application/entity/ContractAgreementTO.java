/*
 *  Copyright 2024-2025 Dataport. All rights reserved. Developed as part of the POSSIBLE project.
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
 */

package eu.possiblex.participantportal.application.entity;

import eu.possiblex.participantportal.application.entity.policies.EnforcementPolicy;
import eu.possiblex.participantportal.business.entity.edc.policy.PolicyOffer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractAgreementTO {
    @Schema(description = "ID of the contract agreement", example = "db4c8c3a-32c7-4887-b3af-0393721345db")
    private String id;

    @Schema(description = "The ID which is used to identify the offering in the EDC catalog. Currently, this is the asset ID, because an asset will only be used in one offering.", example = "8d3c927a-9bb7-4bc8-a3e7-4f9c9a57d571")
    private String assetId;

    @Schema(description = "Details of the asset")
    private AssetDetailsTO assetDetails;

    @Schema(description = "Policy of the contract agreement as retrieved from the EDC")
    private PolicyOffer policy;

    @Schema(description = "List of enforcement policies deduced from the contract agreement policies")
    private List<EnforcementPolicy> enforcementPolicies;

    @Schema(description = "Date when the contract was signed", example = "2021-09-01T12:00:00Z")
    private OffsetDateTime contractSigningDate;

    @Schema(description = "Details of the offering consumer")
    private ContractParticipantDetailsTO consumerDetails;

    @Schema(description = "Details of the offering provider")
    private ContractParticipantDetailsTO providerDetails;

    @Schema(description = "Flag whether the contract agreement is related to an offering containing data resources", example = "true")
    private boolean isDataOffering;

    @Schema(description = "Flag whether the party at hand is the provider of the offering", example = "true")
    private boolean isProvider;
}
