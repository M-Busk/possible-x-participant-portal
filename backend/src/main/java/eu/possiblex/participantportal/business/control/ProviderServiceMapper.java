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

package eu.possiblex.participantportal.business.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.possiblex.participantportal.application.entity.credentials.gx.resources.GxDataResourceCredentialSubject;
import eu.possiblex.participantportal.application.entity.credentials.gx.resources.GxLegitimateInterestCredentialSubject;
import eu.possiblex.participantportal.business.entity.CreateDataOfferingRequestBE;
import eu.possiblex.participantportal.business.entity.CreateServiceOfferingRequestBE;
import eu.possiblex.participantportal.business.entity.credentials.px.PxExtendedDataResourceCredentialSubject;
import eu.possiblex.participantportal.business.entity.credentials.px.PxExtendedServiceOfferingCredentialSubject;
import eu.possiblex.participantportal.business.entity.edc.CreateEdcOfferBE;
import eu.possiblex.participantportal.business.entity.edc.policy.PolicyBlueprint;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ProviderServiceMapper {

    @Mapping(target = "providedBy", source = "request.providedBy")
    @Mapping(target = "aggregationOf", expression = "java(java.util.Collections.emptyList())")
    @Mapping(target = "termsAndConditions", source = "request.termsAndConditions")
    @Mapping(target = "policy", expression = "java(combineSOPolicyAndPolicy(request, policy))")
    @Mapping(target = "dataProtectionRegime", source = "request.dataProtectionRegime")
    @Mapping(target = "dataAccountExport", source = "request.dataAccountExport")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "assetId", source = "assetId")
    @Mapping(target = "providerUrl", source = "providerUrl")
    @Mapping(target = "id", source = "offeringId")
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "context", ignore = true)
    PxExtendedServiceOfferingCredentialSubject getPxExtendedServiceOfferingCredentialSubject(
        CreateServiceOfferingRequestBE request, String offeringId, String assetId, String providerUrl, PolicyBlueprint policy);

    @InheritConfiguration
    @Mapping(target = "aggregationOf", source = "request", qualifiedByName = "gxDataResourceToPxDataResourceList")
    PxExtendedServiceOfferingCredentialSubject getPxExtendedServiceOfferingCredentialSubject(
        CreateDataOfferingRequestBE request, String offeringId, String assetId, String providerUrl, PolicyBlueprint policy);

    @Mapping(target = "assetId", source = "assetId")
    @Mapping(target = "properties.offerId", source = "offerId")
    @Mapping(target = "properties.name", source = "request.name")
    @Mapping(target = "properties.description", source = "request.description")
    @Mapping(target = "properties.providedBy", source = "request.providedBy")
    @Mapping(target = "properties.termsAndConditions", source = "request.termsAndConditions")
    @Mapping(target = "properties.dataProtectionRegime", source = "request.dataProtectionRegime")
    @Mapping(target = "properties.dataAccountExport", source = "request.dataAccountExport")
    @Mapping(target = "properties.contenttype", ignore = true)
    @Mapping(target = "properties.version", ignore = true)
    @Mapping(target = "fileName", constant = "dummy")
    @Mapping(target = "policy", source = "policy")
    CreateEdcOfferBE getCreateEdcOfferBE(CreateServiceOfferingRequestBE request, String offerId, String assetId,
        PolicyBlueprint policy);

    @InheritConfiguration
    @Mapping(target = "properties.copyrightOwnedBy", source = "request.dataResource.copyrightOwnedBy")
    @Mapping(target = "properties.producedBy", source = "request.dataResource.producedBy")
    @Mapping(target = "properties.exposedThrough", source = "request.dataResource.exposedThrough")
    @Mapping(target = "properties.license", source = "request.dataResource.license")
    @Mapping(target = "properties.containsPII", source = "request.dataResource.containsPII")
    @Mapping(target = "properties.dataPolicy", source = "request.dataResource.policy")
    @Mapping(target = "fileName", source = "request.fileName")
    CreateEdcOfferBE getCreateEdcOfferBE(CreateDataOfferingRequestBE request, String offerId, String assetId,
        PolicyBlueprint policy);

    @Mapping(target = "type", ignore = true)
    @Mapping(target = "context", ignore = true)
    @Mapping(target = "legitimateInterest", source = "legitimateInterest")
    PxExtendedDataResourceCredentialSubject gxDataResourceToPxDataResource(
        GxDataResourceCredentialSubject dataResource, GxLegitimateInterestCredentialSubject legitimateInterest);

    @Named("gxDataResourceToPxDataResourceList")
    default List<PxExtendedDataResourceCredentialSubject> gxDataResourceToPxDataResourceList(
        CreateDataOfferingRequestBE request) {
        GxDataResourceCredentialSubject dataResource = request.getDataResource();
        GxLegitimateInterestCredentialSubject legitimateInterest = request.getLegitimateInterestCredentialSubject();
        return List.of(gxDataResourceToPxDataResource(dataResource, legitimateInterest));
    }

    default List<String> policyToStringList(PolicyBlueprint policy) {

        try {
            return List.of(new ObjectMapper().writeValueAsString(policy));
        } catch (JsonProcessingException e) {
            return Collections.emptyList();
        }
    }

    default List<String> combineSOPolicyAndPolicy(CreateServiceOfferingRequestBE request, PolicyBlueprint policy) {

        List<String> policyList = new ArrayList<>();

        if (request.getPolicy() != null) {
            policyList.addAll(request.getPolicy());
        }

        policyList.addAll(policyToStringList(policy));

        return policyList;
    }

}
