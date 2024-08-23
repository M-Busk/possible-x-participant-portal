package eu.possiblex.participantportal.business.entity.fh.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class DctPublisher {

    @JsonProperty("@id")
    private String id;
}
