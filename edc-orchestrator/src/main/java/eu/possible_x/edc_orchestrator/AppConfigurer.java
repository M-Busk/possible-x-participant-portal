package eu.possible_x.edc_orchestrator;

import eu.possible_x.edc_orchestrator.service.EdcClient;
import eu.possible_x.edc_orchestrator.service.FhCatalogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@Slf4j
public class AppConfigurer {

    @Value("${edc.x-api-key}")
    private String edcAccessKey;

    @Value("${edc.mgmt-base-url}")
    private String edcMgmtUrl;

    @Value("${fh.catalog.url}")
    private String fhCatalogUrl;

    @Value("${fh.catalog.secret-key}")
    private String fhCatalogSecretKey;

    @Bean
    public EdcClient edcClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl(edcMgmtUrl)
                .defaultHeader("X-API-Key", edcAccessKey)
                .build();
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builder()
                .exchangeAdapter(WebClientAdapter.create(webClient))
                .build();
        return httpServiceProxyFactory.createClient(EdcClient.class);
    }

    @Bean
    public FhCatalogClient fhCatalogClient() {
        log.info("catalog url: {}", fhCatalogUrl);
        log.info("catalog secret: {}", fhCatalogSecretKey);
        WebClient webClient = WebClient.builder()
                .baseUrl(fhCatalogUrl)
                .build();
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builder()
                .exchangeAdapter(WebClientAdapter.create(webClient))
                .build();
        return httpServiceProxyFactory.createClient(FhCatalogClient.class);
    }
}
