package eu.possible_x.edc_orchestrator.entities.fh.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.possible_x.edc_orchestrator.entities.fh.FhConstants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@Builder
public class DatasetToCatalogRequest {
    private static final Map<String, String> CONTEXT = FhConstants.FH_CONTEXT;

    @JsonProperty("@graph")
    private Graph graph;

    @JsonProperty("@context")
    public Map<String, String> getContext() {
        return CONTEXT;
    }
}
