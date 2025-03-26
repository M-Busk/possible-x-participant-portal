package eu.possiblex.participantportal.business.entity.edc.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DcatDistributionFormat {
    @JsonProperty("@id")
    private String id;
}
