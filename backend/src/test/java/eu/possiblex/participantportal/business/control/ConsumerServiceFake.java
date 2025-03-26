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

import eu.possiblex.participantportal.business.entity.*;
import eu.possiblex.participantportal.business.entity.credentials.px.PxExtendedServiceOfferingCredentialSubject;
import eu.possiblex.participantportal.business.entity.edc.catalog.DcatDataset;
import eu.possiblex.participantportal.business.entity.edc.negotiation.NegotiationState;
import eu.possiblex.participantportal.business.entity.edc.policy.PolicyOffer;
import eu.possiblex.participantportal.business.entity.edc.transfer.TransferProcessState;
import eu.possiblex.participantportal.business.entity.exception.NegotiationFailedException;
import eu.possiblex.participantportal.business.entity.exception.OfferNotFoundException;
import eu.possiblex.participantportal.business.entity.exception.ParticipantNotFoundException;
import eu.possiblex.participantportal.business.entity.exception.TransferFailedException;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

public class ConsumerServiceFake implements ConsumerService {

    public static final String VALID_FH_OFFER_ID = "validFhCatalogOfferId";

    public static final String VALID_EDC_OFFER_ID = "validEdcCatalogOfferId";

    public static final String BAD_EDC_OFFER_ID = "badEdcCatalogOfferId";

    public static final String VALID_ASSET_ID = "validAssetId";

    public static final String MISSING_OFFER_ID = "missingOfferFhCatalogOfferId";

    public static final String MISSING_PARTICIPANT_ID = "missingParticipantFhCatalogParticipantId";

    public static final String BAD_TRANSFER_OFFER_ID = "badTransfer";

    public static final String VALID_COUNTER_PARTY_ADDRESS = "some provider EDC URL";

    public static final String FAKE_DID = "did:web:123";

    public static final String FAKE_EMAIL_ADDRESS = "example@mail.com";

    @Override
    public SelectOfferResponseBE selectContractOffer(SelectOfferRequestBE request) {

        switch (request.getFhCatalogOfferId()) {
            case MISSING_OFFER_ID -> throw new OfferNotFoundException("not found");
            case MISSING_PARTICIPANT_ID -> throw new ParticipantNotFoundException("not found");
            default -> {
                // request worked
            }
        }

        DcatDataset edcCatalogOfferMock = DcatDataset.builder().assetId(VALID_ASSET_ID).name("some name")
            .description("some description").version("v1.2.3").contenttype("application/json").hasPolicy(List.of(
                PolicyOffer.builder().permission(Collections.emptyList()).prohibition(Collections.emptyList())
                    .obligation(Collections.emptyList()).build())).build();

        SelectOfferResponseBE response = new SelectOfferResponseBE();
        response.setEdcOffer(edcCatalogOfferMock);
        response.setCatalogOffering(
            PxExtendedServiceOfferingCredentialSubject.builder().providerUrl(VALID_COUNTER_PARTY_ADDRESS).build());
        response.setProviderDetails(
            ParticipantWithMailBE.builder().did(FAKE_DID).mailAddress(FAKE_EMAIL_ADDRESS).build());
        response.setOfferRetrievalDate(OffsetDateTime.now());

        return response;
    }

    @Override
    public AcceptOfferResponseBE acceptContractOffer(ConsumeOfferRequestBE request) {

        return switch (request.getEdcOfferId()) {
            case MISSING_OFFER_ID -> throw new OfferNotFoundException("not found");
            case BAD_EDC_OFFER_ID ->
                throw new NegotiationFailedException("negotiation failed", Collections.emptyList());
            default ->
                AcceptOfferResponseBE.builder().negotiationState(NegotiationState.FINALIZED).dataOffering(true).build();
        };

    }

    @Override
    public TransferOfferResponseBE transferDataOffer(TransferOfferRequestBE request) {

        return switch (request.getEdcOfferId()) {
            case MISSING_OFFER_ID -> throw new OfferNotFoundException("not found");
            case BAD_TRANSFER_OFFER_ID -> throw new TransferFailedException("transfer failed", Collections.emptyList());
            default -> TransferOfferResponseBE.builder().transferProcessState(TransferProcessState.COMPLETED).build();
        };
    }
}
