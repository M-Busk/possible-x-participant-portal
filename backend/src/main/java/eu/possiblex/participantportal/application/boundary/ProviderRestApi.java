package eu.possiblex.participantportal.application.boundary;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.possiblex.participantportal.application.entity.CreateOfferRequestTO;
import eu.possiblex.participantportal.business.entity.edc.common.IdResponse;
import eu.possiblex.participantportal.business.control.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/provider")
@CrossOrigin("*") // TODO replace this with proper CORS configuration
public class ProviderRestApi {

    private final ProviderService providerService;

    private final ObjectMapper objectMapper;

    public ProviderRestApi(@Autowired ProviderService providerService, @Autowired ObjectMapper objectMapper) {

        this.providerService = providerService;
        this.objectMapper = objectMapper;
    }

    /**
     * POST endpoint to create an offer
     *
     * @return success message
     */
    @PostMapping(value = "/offer", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode createOffer(@RequestBody CreateOfferRequestTO assetRequest) {

        IdResponse response = providerService.createOffer();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("id", response.getId());
        return node;
    }

}
