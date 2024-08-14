package eu.possible_x.edc_orchestrator.entities.edc.negotiation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ContractNegotiation {
    @JsonProperty("@type")
    private String type;

    @JsonProperty("@id")
    private String id;

    @JsonProperty("type")
    private String edcType;
    private String protocol;
    private String state;
    private String counterPartyAddress;
    private List<String> callbackAddresses;
    private String contractAgreementId;

    @JsonProperty("@context")
    private Map<String, String> context;
}
