package eu.possible_x.edc_orchestrator.entities.edc.transfer;

import eu.possible_x.edc_orchestrator.entities.edc.asset.ionoss3extension.IonosS3DataAddress;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IonosS3TransferProcess extends TransferProcess {
    private IonosS3DataAddress dataDestination;
}
