package eu.possiblex.participantportal.application.boundary;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.possiblex.participantportal.application.entity.ConsumeOfferRequestTO;
import eu.possiblex.participantportal.business.entity.edc.asset.ionoss3extension.IonosS3DataAddress;
import eu.possiblex.participantportal.business.control.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumer")
@CrossOrigin("*") // TODO replace this with proper CORS configuration
public class ConsumerRestApi {

    private final ConsumerService consumerService;

    private final ObjectMapper objectMapper;

    public ConsumerRestApi(@Autowired ConsumerService consumerService, @Autowired ObjectMapper objectMapper) {

        this.consumerService = consumerService;
        this.objectMapper = objectMapper;
    }

    /**
     * POST endpoint to accept a Contract Offer
     *
     * @return Data Address of the transferred data
     */
    @PostMapping(value = "/acceptContractOffer", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode acceptContractOffer(@RequestBody ConsumeOfferRequestTO request) {

        IonosS3DataAddress dataAddress = consumerService.acceptContractOffer(request);
        ObjectNode node = objectMapper.createObjectNode();
        node.put("dataAddress", dataAddress.toString());
        return node;
    }
}
