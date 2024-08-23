package eu.possiblex.participantportal.service;

import eu.possiblex.participantportal.business.entity.fh.FhIdResponse;
import eu.possiblex.participantportal.business.entity.fh.catalog.DatasetToCatalogRequest;
import eu.possiblex.participantportal.business.control.FhCatalogClient;

import java.util.Map;

public class FhCatalogClientFake implements FhCatalogClient {
    @Override
    public FhIdResponse addDatasetToFhCatalog(Map<String, String> auth, DatasetToCatalogRequest datasetToCatalogRequest,
        String cat_name, String value_type) {

        return new FhIdResponse("id");
    }
}
