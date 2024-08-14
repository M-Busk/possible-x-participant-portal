package eu.possible_x.edc_orchestrator.entities.edc;

import eu.possible_x.edc_orchestrator.entities.edc.common.IdResponse;
import lombok.Getter;

@Getter
public class EdcIdResponse {

    private final String id;

    public EdcIdResponse(IdResponse response) {
        this.id = response.getId();
    }
}
