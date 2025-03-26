package eu.possiblex.participantportal.business.entity.edc.policy;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import eu.possiblex.participantportal.business.entity.common.JsonLdConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyBlueprint {
     private static final String TYPE = "Set";

    private static final String CONTEXT = JsonLdConstants.POLICY_CONTEXT;

    @Schema(description = "Policy ID", example = "9ca628fb-515a-44ff-90e3-39c34ef4e912")
    @JsonProperty("@id")
    private String id;

    @Schema(description = "List of ODRL permissions as retrieved from the EDC")
    @Builder.Default
    @JsonProperty(JsonLdConstants.ODRL_PREFIX + "permission")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<OdrlPermission> permission = new ArrayList<>();

    @Schema(description = "List of ODRL prohibitions as retrieved from the EDC", example = "[]")
    @Builder.Default
    @JsonProperty(JsonLdConstants.ODRL_PREFIX + "prohibition")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<JsonNode> prohibition = new ArrayList<>(); // replace this with proper classes once needed

    @Schema(description = "List of ODRL obligations as retrieved from the EDC", example = "[]")
    @Builder.Default
    @JsonProperty(JsonLdConstants.ODRL_PREFIX + "obligation")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<JsonNode> obligation = new ArrayList<>(); // replace this with proper classes once needed

    @Schema(description = "JSON-LD type", example = "Set")
    @JsonProperty("@type")
    public String getType() {

        return TYPE;
    }

    @Schema(description = "JSON-LD context", example = "http://www.w3.org/ns/odrl.jsonld")
    @JsonProperty("@context")
    public String getContext() {

        return CONTEXT;
    }
}
