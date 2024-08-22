package eu.possible_x.backend.service;

import java.util.Map;

import eu.possible_x.backend.application.entity.fh.FhIdResponse;
import eu.possible_x.backend.application.entity.fh.catalog.DatasetToCatalogRequest;
import eu.possible_x.backend.business.control.FhCatalogClient;

public class FhCatalogClientFake implements FhCatalogClient {
  @Override
  public FhIdResponse addDatasetToFhCatalog(Map<String, String> auth, DatasetToCatalogRequest datasetToCatalogRequest,
      String cat_name, String value_type) {
    return new FhIdResponse("id");
  }
}
