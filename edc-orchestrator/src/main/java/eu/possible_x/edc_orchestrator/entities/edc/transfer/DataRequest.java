package eu.possible_x.edc_orchestrator.entities.edc.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataRequest {
    @JsonProperty("@type")
    private String type;

    @JsonProperty("@id")
    private String id;
    private String assetId;
    private String contractId;
    private String connectorId;
}
