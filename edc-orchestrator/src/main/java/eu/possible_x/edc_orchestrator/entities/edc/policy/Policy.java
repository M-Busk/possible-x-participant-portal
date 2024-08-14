package eu.possible_x.edc_orchestrator.entities.edc.policy;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.possible_x.edc_orchestrator.entities.edc.EdcConstants;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Policy {

    private static final String TYPE = EdcConstants.ODRL_PREFIX + "Set";

    @JsonProperty("@id")
    private String id;

    @JsonProperty(EdcConstants.ODRL_PREFIX + "permission")
    private List<String> permission; // replace this with proper classes once needed

    @JsonProperty(EdcConstants.ODRL_PREFIX + "prohibition")
    private List<String> prohibition; // replace this with proper classes once needed

    @JsonProperty(EdcConstants.ODRL_PREFIX + "obligation")
    private List<String> obligation; // replace this with proper classes once needed

    @JsonProperty(EdcConstants.ODRL_PREFIX + "target")
    private PolicyTarget target;

    @JsonProperty("@type")
    public String getType() {
        return TYPE;
    }
}
