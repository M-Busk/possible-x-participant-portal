package eu.possible_x.edc_orchestrator.entities.fh.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.possible_x.edc_orchestrator.entities.fh.FhConstants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@Builder
public class Graph {
    private static final ArrayList<String> TYPE = FhConstants.FH_TYPE_DATASET;

    @JsonProperty("@type")
    public ArrayList<String> getType() {
        return TYPE;
    }
    @JsonProperty("dct:description")
    private DctDescription description;
    @JsonProperty("dct:title")
    private DctTitle title;
}
