package eu.possible_x.edc_orchestrator.entities.fh;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FhConstants {
    public static final Map<String, String> FH_CONTEXT = Map.of(
            "dct", "http://purl.org/dc/terms/ ",
            "dcat", "http://www.w3.org/ns/dcat# ");

    public static final ArrayList<String> FH_TYPE_DATASET = new ArrayList<>(List.of(
            "http://w3id.org/gaia-x/gax-trust-framework#DataResource ",
            "dcat:Dataset"));
}
