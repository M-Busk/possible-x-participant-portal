package eu.possible_x.edc_orchestrator.entities.edc.asset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HttpDataAddressProperties {
    private String name;
    private String baseUrl;
    private String type;
}
