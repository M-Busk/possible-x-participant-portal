package eu.possible_x.edc_orchestrator.entities.edc.catalog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.possible_x.edc_orchestrator.entities.edc.EdcConstants;
import eu.possible_x.edc_orchestrator.entities.edc.policy.Policy;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DcatDataset {
    @JsonProperty("@id")
    private String id;

    @JsonProperty("@type")
    private String type;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty(EdcConstants.ODRL_PREFIX + "hasPolicy")
    private List<Policy> hasPolicy;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty(EdcConstants.DCAT_PREFIX + "distribution")
    private List<DcatDistribution> distribution;

    private String version;

    private String name;

    private String description;

    @JsonProperty("id")
    private String assetId;

    private String contenttype;
}
