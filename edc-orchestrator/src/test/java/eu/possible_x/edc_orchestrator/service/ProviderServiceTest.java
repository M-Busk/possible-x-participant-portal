package eu.possible_x.edc_orchestrator.service;

import eu.possible_x.edc_orchestrator.entities.edc.common.IdResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(ProviderServiceTest.TestConfig.class)
class ProviderServiceTest {
    @Autowired
    ProviderService providerService;

    // Test-specific configuration to provide a fake implementation of EdcClient
    @TestConfiguration
    static class TestConfig {
        @Bean
        public EdcClient edcClient() {
            return new EdcClientFake();
        }
    }

    @Test
    void testCreateOffer () {
        IdResponse response = providerService.createOffer();

        assertNotNull(response);
        assertNotNull(response.getId());
        assertFalse(response.getId().isBlank());
    }
}