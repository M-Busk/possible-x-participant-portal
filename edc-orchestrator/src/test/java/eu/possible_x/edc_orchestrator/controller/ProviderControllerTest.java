package eu.possible_x.edc_orchestrator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.possible_x.edc_orchestrator.entities.AssetRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProviderController.class)
class ProviderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private static final String ASSET_NAME = "BestAsset3000";

  @Test
  void shouldReturnMessageOnCreateAsset() throws Exception {
    //given
    AssetRequest assetRequest = AssetRequest.builder().assetName(ASSET_NAME).build();

    //when
    //then
    this.mockMvc.perform(post("/provider/asset")
                    .content(asJsonString(assetRequest))
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(assetRequest.getAssetName())));
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
