package eu.possible_x.edc_orchestrator.service;

import eu.possible_x.edc_orchestrator.entities.edc.common.IdResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@SpringBootTest
@Import(ProviderServiceTest.TestConfig.class)
class ProviderServiceTest {
    @Autowired
    ProviderService providerService;

    @MockBean
    FhCatalogClient fhCatalogClient;

    // Test-specific configuration to provide a fake implementation of EdcClient
    @TestConfiguration
    static class TestConfig {
        @Bean
        public EdcClient edcClient() {
            return new EdcClientFake();
        }
    }

    @BeforeEach
    public void beforeEach() {
        String ADD_DATASET_TO_FH_CATALOG_RESPONSE = "response";
        lenient().when(fhCatalogClient.addDatasetToFhCatalog(any(), any(), any(), any())).thenReturn(ADD_DATASET_TO_FH_CATALOG_RESPONSE);
    }

    @Test
    void testCreateOffer () {
        IdResponse response = providerService.createOffer();

        assertNotNull(response);
        assertNotNull(response.getId());
        assertFalse(response.getId().isBlank());
    }

    @Test
    void testCreateDatasetEntryInFhCatalog () {
        String response = providerService.createDatasetEntryInFhCatalog("test");

        assertNotNull(response);
        assertFalse(response.isBlank());
    }
}