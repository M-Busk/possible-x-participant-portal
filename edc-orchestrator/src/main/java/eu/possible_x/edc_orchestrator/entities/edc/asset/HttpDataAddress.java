package eu.possible_x.edc_orchestrator.entities.edc.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class HttpDataAddress extends DataAddress {

    private static final String TYPE = "HttpData";
    private HttpDataAddressProperties properties;

    @JsonProperty("type")
    public String getDataType() {
        return TYPE;
    }
}
