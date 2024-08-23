package eu.possible_x.backend.application.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AssetRequestTO {

    private Long id;

    private String assetName;
}
