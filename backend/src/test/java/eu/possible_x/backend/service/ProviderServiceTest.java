package eu.possible_x.backend.service;

import eu.possible_x.backend.application.entity.edc.common.IdResponse;
import eu.possible_x.backend.business.control.EdcClient;
import eu.possible_x.backend.business.control.FhCatalogClient;
import eu.possible_x.backend.business.control.ProviderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ContextConfiguration(classes = { ProviderServiceTest.TestConfig.class, ProviderService.class })
class ProviderServiceTest {
    @Autowired
    ProviderService providerService;

    @Autowired
    EdcClient edcClient;

    @Autowired
    FhCatalogClient fhCatalogClient;

    @Test
    void testCreateOffer() {

        IdResponse response = providerService.createOffer();

        verify(fhCatalogClient).addDatasetToFhCatalog(any(), any(), any(), any());

        verify(edcClient).createAsset(any());
        verify(edcClient).createPolicy(any());
        verify(edcClient).createContractDefinition(any());

        assertNotNull(response);
        assertNotNull(response.getId());
        assertFalse(response.getId().isBlank());
    }

    // Test-specific configuration to provide a fake implementation of EdcClient
    @TestConfiguration
    static class TestConfig {
        @Bean
        public EdcClient edcClient() {

            return Mockito.spy(new EdcClientFake());
        }

        @Bean
        public FhCatalogClient fhCatalogClient() {

            return Mockito.spy(new FhCatalogClientFake());
        }
    }

}