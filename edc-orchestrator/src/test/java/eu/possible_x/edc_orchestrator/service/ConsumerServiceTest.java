package eu.possible_x.edc_orchestrator.service;

import eu.possible_x.edc_orchestrator.entities.edc.asset.ionoss3extension.IonosS3DataAddress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(ConsumerServiceTest.TestConfig.class)
class ConsumerServiceTest {
  @Autowired
  ConsumerService consumerService;

  // Test-specific configuration to provide a fake implementation of EdcClient
  @TestConfiguration
  static class TestConfig {
    @Bean
    public EdcClient edcClient() {
      return new EdcClientFake();
    }
  }

  @Test
  void shouldAcceptContractOffer() {
    IonosS3DataAddress response = consumerService.acceptContractOffer();

    assertNotNull(response);
  }
}
