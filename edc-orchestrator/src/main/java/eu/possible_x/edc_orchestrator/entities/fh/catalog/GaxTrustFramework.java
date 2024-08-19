package eu.possible_x.edc_orchestrator.entities.fh.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
public class GaxTrustFramework {
    @JsonProperty("@id")
    private String id;
}
