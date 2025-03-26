package eu.possiblex.participantportal.business.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.possiblex.participantportal.application.entity.credentials.gx.datatypes.NodeKindIRITypeId;
import eu.possiblex.participantportal.business.entity.*;
import eu.possiblex.participantportal.business.entity.credentials.px.PxExtendedDataResourceCredentialSubject;
import eu.possiblex.participantportal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubjectSubset;
import eu.possiblex.participantportal.business.entity.credentials.px.PxExtendedServiceOfferingCredentialSubject;
import eu.possiblex.participantportal.business.entity.edc.catalog.DcatCatalog;
import eu.possiblex.participantportal.business.entity.edc.catalog.DcatDataset;
import eu.possiblex.participantportal.business.entity.edc.policy.PolicyOffer;
import eu.possiblex.participantportal.business.entity.exception.NegotiationFailedException;
import eu.possiblex.participantportal.business.entity.exception.OfferNotFoundException;
import eu.possiblex.participantportal.business.entity.exception.ParticipantNotFoundException;
import eu.possiblex.participantportal.business.entity.exception.TransferFailedException;
import eu.possiblex.participantportal.business.entity.fh.ParticipantDetailsSparqlQueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.test.context.ContextConfiguration;

import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = { ConsumerServiceTest.TestConfig.class, ConsumerServiceImpl.class })
class ConsumerServiceTest {

    @Captor
    ArgumentCaptor<Collection<String>> idCaptor;

    @Autowired
    private ConsumerService sut;

    @Autowired
    private EdcClient edcClient;

    @Autowired
    private FhCatalogClient fhCatalogClient;

    @BeforeEach
    void setUp() {

        reset(edcClient);
        reset(fhCatalogClient);
    }

    @Test
    void selectContractOfferSucceedsDataOffering() {

        // GIVEN

        PxExtendedServiceOfferingCredentialSubject fhCatalogOffer = getPxExtendedServiceOfferingCredentialSubject(true);
        OffsetDateTime offerRetrievalDate = OffsetDateTime.now();
        Mockito.when(fhCatalogClient.getFhCatalogOffer(EdcClientFake.FAKE_ID))
            .thenReturn(new OfferRetrievalResponseBE(fhCatalogOffer, offerRetrievalDate));
        DcatCatalog catalog = new DcatCatalog();
        DcatDataset dataset = new DcatDataset();
        dataset.setId(EdcClientFake.FAKE_ID);
        dataset.setAssetId(EdcClientFake.FAKE_ID);
        dataset.setName("correctName");
        dataset.setContenttype("correctContentType");
        dataset.setDescription("correctDescription");
        dataset.setHasPolicy(Collections.emptyList());
        catalog.setDataset(List.of(dataset));
        Mockito.when(edcClient.queryCatalog(any())).thenReturn(catalog);
        Map<String, ParticipantDetailsSparqlQueryResult> participantDetails = Map.of(FhCatalogClientFake.FAKE_DID,
            ParticipantDetailsSparqlQueryResult.builder().uri(FhCatalogClientFake.FAKE_DID)
                .mailAddress(FhCatalogClientFake.FAKE_EMAIL_ADDRESS).build(), FhCatalogClientFake.FAKE_PROVIDER_ID,
            ParticipantDetailsSparqlQueryResult.builder().uri(FhCatalogClientFake.FAKE_PROVIDER_ID)
                .mailAddress(FhCatalogClientFake.FAKE_EMAIL_ADDRESS).build());
        Mockito.when(fhCatalogClient.getParticipantDetailsByIds(any())).thenReturn(participantDetails);

        // WHEN

        SelectOfferResponseBE response = sut.selectContractOffer(
            SelectOfferRequestBE.builder().fhCatalogOfferId(EdcClientFake.FAKE_ID).build());

        // THEN

        verify(edcClient).queryCatalog(any());
        verify(edcClient, times(0)).initiateTransfer(any());
        verify(fhCatalogClient, times(1)).getParticipantDetailsByIds(idCaptor.capture());

        Collection<String> ids = idCaptor.getValue();
        assertNotNull(ids);
        assertEquals(1, ids.size());
        assertTrue(ids.contains(FhCatalogClientFake.FAKE_DID));

        assertNotNull(response);
        assertEquals(FhCatalogClientFake.FAKE_DID, response.getProviderDetails().getDid());
        assertEquals(FhCatalogClientFake.FAKE_EMAIL_ADDRESS, response.getProviderDetails().getMailAddress());
        assertEquals(offerRetrievalDate, response.getOfferRetrievalDate());
    }

    @Test
    void selectContractOfferSucceedsServiceOffering() {

        // GIVEN

        PxExtendedServiceOfferingCredentialSubject fhCatalogOffer = getPxExtendedServiceOfferingCredentialSubject(
            false);
        OffsetDateTime offerRetrievalDate = OffsetDateTime.now();
        Mockito.when(fhCatalogClient.getFhCatalogOffer(EdcClientFake.FAKE_ID))
            .thenReturn(new OfferRetrievalResponseBE(fhCatalogOffer, offerRetrievalDate));
        DcatCatalog catalog = new DcatCatalog();
        DcatDataset dataset = new DcatDataset();
        dataset.setId(EdcClientFake.FAKE_ID);
        dataset.setAssetId(EdcClientFake.FAKE_ID);
        dataset.setName("correctName");
        dataset.setContenttype("correctContentType");
        dataset.setDescription("correctDescription");
        dataset.setHasPolicy(Collections.emptyList());
        catalog.setDataset(List.of(dataset));
        Mockito.when(edcClient.queryCatalog(any())).thenReturn(catalog);
        Map<String, ParticipantDetailsSparqlQueryResult> participantDetails = Map.of(FhCatalogClientFake.FAKE_DID,
            ParticipantDetailsSparqlQueryResult.builder().uri(FhCatalogClientFake.FAKE_DID)
                .mailAddress(FhCatalogClientFake.FAKE_EMAIL_ADDRESS).build());
        Mockito.when(fhCatalogClient.getParticipantDetailsByIds(any())).thenReturn(participantDetails);

        // WHEN

        SelectOfferResponseBE response = sut.selectContractOffer(
            SelectOfferRequestBE.builder().fhCatalogOfferId(EdcClientFake.FAKE_ID).build());

        // THEN

        verify(edcClient).queryCatalog(any());
        verify(edcClient, times(0)).initiateTransfer(any());
        verify(fhCatalogClient, times(1)).getParticipantDetailsByIds(idCaptor.capture());

        Collection<String> ids = idCaptor.getValue();
        assertNotNull(ids);
        assertEquals(1, ids.size());
        assertTrue(ids.contains(FhCatalogClientFake.FAKE_DID));

        assertNotNull(response);
        assertEquals(FhCatalogClientFake.FAKE_DID, response.getProviderDetails().getDid());
        assertEquals(FhCatalogClientFake.FAKE_EMAIL_ADDRESS, response.getProviderDetails().getMailAddress());
        assertEquals(offerRetrievalDate, response.getOfferRetrievalDate());
    }

    @Test
    void selectContractOfferFailsServiceOfferingProviderNotFound() {

        // GIVEN

        PxExtendedServiceOfferingCredentialSubject fhCatalogOffer = getPxExtendedServiceOfferingCredentialSubject(
            false);
        OffsetDateTime offerRetrievalDate = OffsetDateTime.now();
        Mockito.when(fhCatalogClient.getFhCatalogOffer(EdcClientFake.FAKE_ID))
            .thenReturn(new OfferRetrievalResponseBE(fhCatalogOffer, offerRetrievalDate));
        DcatCatalog catalog = new DcatCatalog();
        DcatDataset dataset = new DcatDataset();
        dataset.setId(EdcClientFake.FAKE_ID);
        dataset.setAssetId(EdcClientFake.FAKE_ID);
        dataset.setName("correctName");
        dataset.setContenttype("correctContentType");
        dataset.setDescription("correctDescription");
        dataset.setHasPolicy(Collections.emptyList());
        catalog.setDataset(List.of(dataset));
        Mockito.when(edcClient.queryCatalog(any())).thenReturn(catalog);
        Mockito.when(fhCatalogClient.getParticipantDetailsByIds(any())).thenReturn(new HashMap<>());

        SelectOfferRequestBE request = SelectOfferRequestBE.builder().fhCatalogOfferId(EdcClientFake.FAKE_ID).build();

        // WHEN / THEN

        assertThrows(ParticipantNotFoundException.class, () -> sut.selectContractOffer(request));
    }

    @Test
    void acceptContractOfferSucceeds() {

        // GIVEN

        PxExtendedLegalParticipantCredentialSubjectSubset fhCatalogParticipant = new PxExtendedLegalParticipantCredentialSubjectSubset();
        fhCatalogParticipant.setId(FhCatalogClientFake.FAKE_PROVIDER_ID);
        fhCatalogParticipant.setMailAddress(FhCatalogClientFake.FAKE_EMAIL_ADDRESS);
        Mockito.when(fhCatalogClient.getFhCatalogParticipant(FhCatalogClientFake.FAKE_PROVIDER_ID))
            .thenReturn(fhCatalogParticipant);
        DcatCatalog catalog = new DcatCatalog();
        DcatDataset dataset = new DcatDataset();
        dataset.setId(EdcClientFake.FAKE_ID);
        dataset.setAssetId(EdcClientFake.FAKE_ID);
        dataset.setName("correctName");
        dataset.setContenttype("correctContentType");
        dataset.setDescription("correctDescription");
        dataset.setHasPolicy(List.of(new PolicyOffer()));
        catalog.setDataset(List.of(dataset));
        Mockito.when(edcClient.queryCatalog(any())).thenReturn(catalog);

        // WHEN

        AcceptOfferResponseBE response = sut.acceptContractOffer(
            ConsumeOfferRequestBE.builder().counterPartyAddress("http://example.com").edcOfferId(EdcClientFake.FAKE_ID)
                .dataOffering(true).build());

        // THEN

        verify(edcClient).negotiateOffer(any());

        assertNotNull(response);
    }

    @Test
    void acceptContractOfferSucceedsNoTransfer() {

        // GIVEN

        PxExtendedLegalParticipantCredentialSubjectSubset fhCatalogParticipant = new PxExtendedLegalParticipantCredentialSubjectSubset();
        fhCatalogParticipant.setId(FhCatalogClientFake.FAKE_PROVIDER_ID);
        fhCatalogParticipant.setMailAddress(FhCatalogClientFake.FAKE_EMAIL_ADDRESS);
        Mockito.when(fhCatalogClient.getFhCatalogParticipant(FhCatalogClientFake.FAKE_PROVIDER_ID))
            .thenReturn(fhCatalogParticipant);
        DcatCatalog catalog = new DcatCatalog();
        DcatDataset dataset = new DcatDataset();
        dataset.setId(EdcClientFake.FAKE_ID);
        dataset.setAssetId(EdcClientFake.FAKE_ID);
        dataset.setName("correctName");
        dataset.setContenttype("correctContentType");
        dataset.setDescription("correctDescription");
        dataset.setHasPolicy(List.of(new PolicyOffer()));
        catalog.setDataset(List.of(dataset));
        Mockito.when(edcClient.queryCatalog(any())).thenReturn(catalog);

        // WHEN

        AcceptOfferResponseBE response = sut.acceptContractOffer(
            ConsumeOfferRequestBE.builder().counterPartyAddress("http://example.com").edcOfferId(EdcClientFake.FAKE_ID)
                .dataOffering(false).build());

        // THEN

        verify(edcClient).negotiateOffer(any());

        assertNotNull(response);
    }

    @Test
    void acceptContractOfferNotFound() {

        ConsumeOfferRequestBE request = ConsumeOfferRequestBE.builder().counterPartyAddress("http://example.com")
            .edcOfferId("someUnknownId").build();

        assertThrows(OfferNotFoundException.class, () -> sut.acceptContractOffer(request));
    }

    @Test
    void acceptContractOfferBadNegotiation() {

        // GIVEN

        DcatCatalog catalog = new DcatCatalog();
        DcatDataset dataset = new DcatDataset();
        dataset.setId(EdcClientFake.BAD_NEGOTIATION_ID);
        dataset.setAssetId(EdcClientFake.BAD_NEGOTIATION_ID);
        dataset.setName("correctName");
        dataset.setContenttype("correctContentType");
        dataset.setDescription("correctDescription");
        dataset.setHasPolicy(List.of(new PolicyOffer()));
        catalog.setDataset(List.of(dataset));
        Mockito.when(edcClient.queryCatalog(any())).thenReturn(catalog);

        ConsumeOfferRequestBE request = ConsumeOfferRequestBE.builder().counterPartyAddress("http://example.com")
            .edcOfferId(EdcClientFake.BAD_NEGOTIATION_ID).build();

        // WHEN / THEN

        assertThrows(NegotiationFailedException.class, () -> sut.acceptContractOffer(request));
    }

    @Test
    void acceptContractOfferTimedOutNegotiation() {

        // GIVEN

        DcatCatalog catalog = new DcatCatalog();
        DcatDataset dataset = new DcatDataset();
        dataset.setId(EdcClientFake.TIMED_OUT_NEGOTIATION_ID);
        dataset.setAssetId(EdcClientFake.TIMED_OUT_NEGOTIATION_ID);
        dataset.setName("correctName");
        dataset.setContenttype("correctContentType");
        dataset.setDescription("correctDescription");
        dataset.setHasPolicy(List.of(new PolicyOffer()));
        catalog.setDataset(List.of(dataset));
        Mockito.when(edcClient.queryCatalog(any())).thenReturn(catalog);

        ConsumeOfferRequestBE request = ConsumeOfferRequestBE.builder().counterPartyAddress("http://example.com")
            .edcOfferId(EdcClientFake.TIMED_OUT_NEGOTIATION_ID).build();


        // WHEN / THEN
        assertThrows(NegotiationFailedException.class, () -> sut.acceptContractOffer(request));
    }

    @Test
    void transferDataOfferTransferFailed() {

        // GIVEN

        DcatCatalog catalog = new DcatCatalog();
        DcatDataset dataset = new DcatDataset();
        dataset.setId(EdcClientFake.BAD_TRANSFER_ID);
        dataset.setAssetId(EdcClientFake.BAD_TRANSFER_ID);
        dataset.setName("correctName");
        dataset.setContenttype("correctContentType");
        dataset.setDescription("correctDescription");
        dataset.setHasPolicy(Collections.emptyList());
        catalog.setDataset(List.of(dataset));
        Mockito.when(edcClient.queryCatalog(any())).thenReturn(catalog);

        TransferOfferRequestBE request = TransferOfferRequestBE.builder().counterPartyAddress("http://example.com")
            .edcOfferId(EdcClientFake.BAD_TRANSFER_ID).contractAgreementId(EdcClientFake.VALID_AGREEMENT_ID).build();

        // WHEN / THEN

        assertThrows(TransferFailedException.class, () -> sut.transferDataOffer(request));
    }

    @Test
    void transferDataOfferTimedOutTransfer() {

        // GIVEN

        DcatCatalog catalog = new DcatCatalog();
        DcatDataset dataset = new DcatDataset();
        dataset.setId(EdcClientFake.TIMED_OUT_TRANSFER_ID);
        dataset.setAssetId(EdcClientFake.TIMED_OUT_TRANSFER_ID);
        dataset.setName("correctName");
        dataset.setContenttype("correctContentType");
        dataset.setDescription("correctDescription");
        dataset.setHasPolicy(Collections.emptyList());
        catalog.setDataset(List.of(dataset));
        Mockito.when(edcClient.queryCatalog(any())).thenReturn(catalog);

        TransferOfferRequestBE request = TransferOfferRequestBE.builder().counterPartyAddress("http://example.com")
            .edcOfferId(EdcClientFake.TIMED_OUT_TRANSFER_ID).contractAgreementId(EdcClientFake.VALID_AGREEMENT_ID).build();

        // WHEN / THEN

        assertThrows(TransferFailedException.class, () -> sut.transferDataOffer(request));
    }

    @Test
    void transferDataOffer() {

        // GIVEN

        DcatCatalog catalog = new DcatCatalog();
        DcatDataset dataset = new DcatDataset();
        dataset.setId(EdcClientFake.FAKE_ID);
        dataset.setAssetId(EdcClientFake.FAKE_ID);
        dataset.setName("correctName");
        dataset.setContenttype("correctContentType");
        dataset.setDescription("correctDescription");
        dataset.setHasPolicy(Collections.emptyList());
        catalog.setDataset(List.of(dataset));
        Mockito.when(edcClient.queryCatalog(any())).thenReturn(catalog);

        // WHEN

        TransferOfferResponseBE response = sut.transferDataOffer(
            TransferOfferRequestBE.builder().counterPartyAddress("http://example.com").edcOfferId(EdcClientFake.FAKE_ID)
                .contractAgreementId(EdcClientFake.VALID_AGREEMENT_ID).build());

        // THEN

        verify(edcClient).initiateTransfer(any());

        assertNotNull(response);
    }

    private PxExtendedServiceOfferingCredentialSubject getPxExtendedServiceOfferingCredentialSubject(
        boolean isDataOffering) {

        PxExtendedServiceOfferingCredentialSubject fhCatalogOffer = new PxExtendedServiceOfferingCredentialSubject();
        fhCatalogOffer.setAssetId(EdcClientFake.FAKE_ID);
        fhCatalogOffer.setProvidedBy(new NodeKindIRITypeId(FhCatalogClientFake.FAKE_DID));

        if (isDataOffering) {
            PxExtendedDataResourceCredentialSubject dataResource = new PxExtendedDataResourceCredentialSubject();
            dataResource.setCopyrightOwnedBy(List.of(FhCatalogClientFake.FAKE_DID));
            dataResource.setProducedBy(new NodeKindIRITypeId(FhCatalogClientFake.FAKE_PROVIDER_ID));
            fhCatalogOffer.setAggregationOf(List.of(dataResource));
        }

        return fhCatalogOffer;
    }

    // Test-specific configuration to provide mocks
    @TestConfiguration
    static class TestConfig {
        @MockBean
        private TaskScheduler taskScheduler;

        @Bean
        public EdcClient edcClient() {

            return Mockito.spy(new EdcClientFake());
        }

        @Bean
        public EnforcementPolicyParserService enforcementPolicyParserService() {

            return Mockito.spy(new EnforcementPolicyParserServiceFake());
        }

        @Bean
        public ObjectMapper objectMapper() {

            return new ObjectMapper();
        }

        @Bean
        public FhCatalogClient fhCatalogClient() {

            return Mockito.mock(FhCatalogClient.class);
        }

        @Bean
        public ConsumerServiceMapper consumerServiceMapper() {

            return Mappers.getMapper(ConsumerServiceMapper.class);
        }
    }
}
