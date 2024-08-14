package eu.possible_x.edc_orchestrator.entities.edc.transfer;

import eu.possible_x.edc_orchestrator.entities.edc.asset.HttpDataAddress;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpTransferProcess extends TransferProcess{
    private HttpDataAddress dataDestination;
}
