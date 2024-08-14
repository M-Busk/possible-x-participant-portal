package eu.possible_x.edc_orchestrator.entities.edc.asset;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetProperties {
    private String name;

    private String description;

    private String version;

    private String contenttype;
}
