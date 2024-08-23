package eu.possiblex.participantportal.application.entity;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateOfferRequestTO {

    private String offerType;

    private String offerName;

    private String offerDescription;

    private String fileName;

    private JsonNode policy;

}
