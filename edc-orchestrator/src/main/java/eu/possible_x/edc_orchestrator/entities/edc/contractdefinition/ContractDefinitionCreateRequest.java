package eu.possible_x.edc_orchestrator.entities.edc.contractdefinition;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.possible_x.edc_orchestrator.entities.edc.EdcConstants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder
public class ContractDefinitionCreateRequest {
    private static final Map<String, String> CONTEXT = EdcConstants.EDC_CONTEXT;

    private static final String TYPE = "ContractDefinition";

    private List<Criterion> assetsSelector;

    @JsonProperty("@id")
    private String id;

    private String accessPolicyId;

    private String contractPolicyId;

    @JsonProperty("@context")
    public Map<String, String> getContext() {
        return CONTEXT;
    }

    @JsonProperty("@type")
    public String getType() {
        return TYPE;
    }
}
