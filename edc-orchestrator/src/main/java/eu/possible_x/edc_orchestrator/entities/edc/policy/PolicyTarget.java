package eu.possible_x.edc_orchestrator.entities.edc.policy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PolicyTarget {
    @JsonProperty("@id")
    private String id;
}
