package eu.possible_x.edc_orchestrator.entities.edc.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.possible_x.edc_orchestrator.entities.edc.EdcConstants;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class DcatDistribution {
    @JsonProperty("@type")
    private String type;

    @JsonProperty(EdcConstants.DCT_PREFIX + "format")
    private Map<String, String> format;

    @JsonProperty(EdcConstants.DCAT_PREFIX + "accessService")
    private String accessService;
}
