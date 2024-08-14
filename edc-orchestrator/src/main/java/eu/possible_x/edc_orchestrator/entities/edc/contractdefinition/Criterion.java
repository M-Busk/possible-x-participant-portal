package eu.possible_x.edc_orchestrator.entities.edc.contractdefinition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Criterion {

    @JsonProperty("@type")
    private String type = "CriterionDto";

    private Object operandLeft;
    private String operator;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private Object operandRight;

    @Builder
    public Criterion(Object operandLeft,
                     String operator,
                     Object operandRight) {
        this.operandLeft = operandLeft;
        this.operator = operator;
        this.operandRight = operandRight;
    }
}
