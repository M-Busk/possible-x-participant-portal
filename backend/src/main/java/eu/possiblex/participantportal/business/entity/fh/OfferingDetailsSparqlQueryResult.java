/*
 *  Copyright 2024-2025 Dataport. All rights reserved. Developed as part of the POSSIBLE project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.possiblex.participantportal.business.entity.fh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferingDetailsSparqlQueryResult {
    @JsonDeserialize(using = CatalogLiteralDeserializer.class)
    private String uri;

    @JsonDeserialize(using = CatalogLiteralDeserializer.class)
    private String assetId;

    @JsonDeserialize(using = CatalogLiteralDeserializer.class)
    private String name;

    @JsonDeserialize(using = CatalogLiteralDeserializer.class)
    private String description;

    @JsonDeserialize(using = CatalogLiteralDeserializer.class)
    private String providerUrl;

    @JsonDeserialize(using = CatalogLiteralDeserializer.class)
    private String aggregationOf;
}
