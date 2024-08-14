package eu.possible_x.edc_orchestrator.entities.edc.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class IdResponse {
    @JsonProperty("@type")
    private String type;

    @JsonProperty("@id")
    private String id;
    private long createdAt;

    @JsonProperty("@context")
    private Map<String, String> context;
}
