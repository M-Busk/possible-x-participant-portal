package eu.possible_x.backend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import eu.possible_x.backend.entities.AssetRequest;
import eu.possible_x.backend.entities.edc.common.IdResponse;
import eu.possible_x.backend.service.ProviderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/provider")
@CrossOrigin("*") // TODO replace this with proper CORS configuration
public class ProviderController {

  private final ProviderService providerService;
  private final ObjectMapper objectMapper;

  public ProviderController(
      @Autowired ProviderService providerService,
      @Autowired ObjectMapper objectMapper
  ) {

      this.providerService = providerService;
      this.objectMapper = objectMapper;
  }

  /**
   * POST endpoint to create an asset
   *
   * @param assetRequest: request to create an asset
   * @return success message
   */
  @PostMapping(value = "/asset", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> createAsset(@RequestBody AssetRequest assetRequest) {
   return ResponseEntity.ok("Success: create the asset with name: " + assetRequest.getAssetName());
  }

  /**
   * POST endpoint to create an offer
   *
   * @return success message
   */
  @PostMapping(value = "/offer", produces = MediaType.APPLICATION_JSON_VALUE)
  public JsonNode createOffer() {
    IdResponse response = providerService.createOffer();
    ObjectNode node = objectMapper.createObjectNode();
    node.put("id", response.getId());
    return node;
  }

}
