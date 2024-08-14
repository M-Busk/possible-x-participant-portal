package eu.possible_x.edc_orchestrator.entities.edc.negotiation;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.possible_x.edc_orchestrator.entities.edc.policy.Policy;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ContractOffer {
    private static final String TYPE = "ContractOfferDescription";

    private String offerId;
    private String assetId;
    private Policy policy;

    @JsonProperty("@type")
    public String getType() {
        return TYPE;
    }
}
