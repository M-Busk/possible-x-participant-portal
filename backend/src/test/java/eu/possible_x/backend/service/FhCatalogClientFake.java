package eu.possible_x.backend.service;

import java.util.Map;

import eu.possible_x.backend.entities.fh.FhIdResponse;
import eu.possible_x.backend.entities.fh.catalog.DatasetToCatalogRequest;
import eu.possible_x.backend.service.FhCatalogClient;

public class FhCatalogClientFake implements FhCatalogClient {
  @Override
  public FhIdResponse addDatasetToFhCatalog(Map<String, String> auth, DatasetToCatalogRequest datasetToCatalogRequest,
      String cat_name, String value_type) {
    return new FhIdResponse("id");
  }
}
